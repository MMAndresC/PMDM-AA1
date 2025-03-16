package com.svalero.tournaments.view.user;

import static com.svalero.tournaments.constants.Constants.DATABASE_NAME;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.UserListAdapter;
import com.svalero.tournaments.dao.AppDatabase;
import com.svalero.tournaments.domain.SpinnerOption;
import com.svalero.tournaments.domain.UserData;
import com.svalero.tournaments.util.ParseUtil;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.view.tournament.ListNextTournamentsView;

import java.util.ArrayList;
import java.util.List;

public class ZoneUserView extends AppCompatActivity {

    private UserData userData;
    private String username;
    private final int PICK_PICTURE = 1;
    private Uri pictureUri;
    private ImageView imageView;
    private List<UserData> usersList;
    private UserListAdapter adapterRecycler;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_user_view);

        imageView = findViewById(R.id.imageUser);

        db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        //Load spinner with position 0 as default
        ArrayList<SpinnerOption> options = getRegionOptions();
        ArrayAdapter<SpinnerOption> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner regionSpinner = findViewById(R.id.editRegionUserData);
        regionSpinner.setAdapter(adapter);

        usersList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listUserRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterRecycler = new UserListAdapter(usersList);
        recyclerView.setAdapter(adapterRecycler);

        username = SharedPreferencesUtil.getCustomSharedPreferences(this, "username");
        if(username != null){
            //DB local
            try {
                userData = db.userDataDao().getUserData(username);
                loadData();
                List<UserData> tempList = db.userDataDao().getUsers();
                if(!tempList.isEmpty()){
                    usersList.addAll(tempList);
                }
            } catch (SQLiteConstraintException sce) {
                String message = getString(R.string.db_error);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ListNextTournamentsView.class);
                startActivity(intent);
                finish();
            }
        } else{
            Intent intent = new Intent(this, ListNextTournamentsView.class);
            startActivity(intent);
            finish();
        }

        //Ask for consent to use camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PICK_PICTURE);
        }

        // Listener on click to select image from gallery
        imageView.setOnClickListener(ev -> selectImageFromGallery());
    }

    /**
     *
     *COSAS PENDIENTES:
     * - equipos favoritos, guardarlo en la base de datos
     * -los idiomas
     * -ordenar
     * -buscar
     * -vista detalle del equipo con los juagdores
     */

    //Create confirm dialog to delete tournament
    private void createDialog(String message, String action){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String definitiveAction = "";
                                if(action.equals("save") && userData != null)
                                    definitiveAction = "update";
                                else definitiveAction = action;
                                actionsUserData(definitiveAction);
                            }})
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }

    private void actionsUserData(String action){
        try {
            String message = "";
            if(action.equals("save")){
                getData();
                db.userDataDao().insert(userData);
                message = getString(R.string.user_data_saved);
                usersList.add(userData);
                adapterRecycler.notifyItemInserted(usersList.size() - 1);
            }
            if(action.equals("update")){
                getData();
                db.userDataDao().update(userData.getAlias(), userData.getImage(), userData.getRegion(), userData.getMainRole(), username);
                message = getString(R.string.user_data_saved);
                int index = findIndexInUsersList();
                if(index != -1){
                    usersList.set(index, userData);
                    adapterRecycler.notifyItemChanged(index);
                }
            }
            if(action.equals("delete")){
                db.userDataDao().deleteByName(username);
                resetView();
                userData = null;
                message = getString(R.string.user_data_deleted);
                int index = findIndexInUsersList();
                if(index != -1){
                    usersList.remove(index);
                    adapterRecycler.notifyItemRemoved(index);
                }
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException sce) {
            String message = getString(R.string.db_error);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    //Add menu action_bar_user_data in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_user_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveUserDataMenu) {
            createDialog(getString(R.string.save_user_data), "save");
        }else if(item.getItemId() == R.id.deleteUserDataMenu) {
            createDialog(getString(R.string.delete_user_data), "delete");
        }else if(item.getItemId() == R.id.photoUserDataMenu){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            }
        }
        return true;
    }

    // Open camera and take photo
    private final ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        imageView.setImageURI(pictureUri);
                    }
                }
            });

    private void launchCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, getString(R.string.new_image));
        values.put(MediaStore.Images.Media.DESCRIPTION, getString(R.string.camera));
        pictureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startCamera.launch(cameraIntent);
    }

    // Get image from gallery when click on image view
    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    imageView.setImageURI(imageUri);
                }
            }
    );

    public void selectImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(intent);
    }

    public ArrayList<SpinnerOption> getRegionOptions(){
        ArrayList<SpinnerOption> options = new ArrayList<>();
        options.add(new SpinnerOption(0, "Region"));
        options.add(new SpinnerOption(1, getString(R.string.region_NA)));
        options.add(new SpinnerOption(2, getString(R.string.region_SA)));
        options.add(new SpinnerOption(3, getString(R.string.region_EU)));
        options.add(new SpinnerOption(4, getString(R.string.region_AS)));
        options.add(new SpinnerOption(5, getString(R.string.region_OC)));
        return options;
    }

    private void loadData(){
        if(userData == null) return;
        String alias = userData.getAlias();
        if(alias == null) alias = "";
        ((EditText) findViewById(R.id.editAliasUserData)).setText(alias);
        ((Spinner) findViewById(R.id.editRegionUserData)).setSelection(userData.getRegion());
        String role = userData.getMainRole();
        if(role.equals("support"))
            ((RadioButton) findViewById(R.id.radioButtonSupport)).setChecked(true);
        else if(role.equals("dps"))
            ((RadioButton) findViewById(R.id.radioButtonDps)).setChecked(true);
        else if(role.equals("tank"))
            ((RadioButton) findViewById(R.id.radioButtonTank)).setChecked(true);
        if(userData.getImage() != null){
            imageView.setImageBitmap(ParseUtil.byteArrayToBitmap(userData.getImage()));
        }
    }

    private void getData(){
        userData = new UserData();
        userData.setUsername(username);
        String alias = ((EditText) findViewById(R.id.editAliasUserData)).getText().toString();
        userData.setAlias(alias);
        userData.setRegion(getRegionFromSpinner());
        userData.setMainRole(getMainRoleFromRadioGroup());
        if(!isDefaultImage()){
            Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            userData.setImage(ParseUtil.bitmapToByteArray(imageBitmap));
        }
    }

    private int getRegionFromSpinner() {
        Spinner spinner = findViewById(R.id.editRegionUserData);
        SpinnerOption regionSelected = (SpinnerOption) spinner.getSelectedItem();
        return regionSelected.getId();
    }

    private String getMainRoleFromRadioGroup() {
        if(((RadioButton) findViewById(R.id.radioButtonTank)).isChecked())
            return "tank";
        else if(((RadioButton) findViewById(R.id.radioButtonDps)).isChecked())
            return  "dps";
        else if(((RadioButton) findViewById(R.id.radioButtonSupport)).isChecked())
            return  "support";
        return "";
    }

    private void resetView() {
        ((EditText) findViewById(R.id.editAliasUserData)).setText("");
        ((Spinner) findViewById(R.id.editRegionUserData)).setSelection(0);
        ((RadioButton) findViewById(R.id.radioButtonTank)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioButtonDps)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioButtonSupport)).setChecked(false);
        imageView.setImageResource(R.drawable.no_photos);
    }

    private boolean isDefaultImage(){
        Drawable imageInImageView = imageView.getDrawable();
        Drawable defaultImage = getResources().getDrawable(R.drawable.no_photos, null);
        if(imageInImageView != null && defaultImage != null){
            //Avoid error if image is not BitMapDrawable
            if(imageInImageView instanceof BitmapDrawable && defaultImage instanceof BitmapDrawable){
                Bitmap imageBitmap = ((BitmapDrawable) imageInImageView).getBitmap();
                Bitmap defaultBitmap = ((BitmapDrawable) defaultImage).getBitmap();
                return imageBitmap.sameAs(defaultBitmap);
            }
        }
        //Default not save image in DB
        return true;
    }

    private int findIndexInUsersList(){
        if(username == null) return  -1;
        for(int i = 0; i < usersList.size(); i++){
            UserData data = usersList.get(i);
            if(data.getUsername().equals(username))
                return i;
        }
        return -1;
    }

}