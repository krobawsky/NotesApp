package tecsup.ricardob.teoria07.notesapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import tecsup.ricardob.teoria07.notesapp.Datos.Note;
import tecsup.ricardob.teoria07.notesapp.Datos.NoteRepository;
import tecsup.ricardob.teoria07.notesapp.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText titleInput;
    private EditText contentInput;

    private Button btn;
    ImageView imageView;

    byte[] imagen;
    private static final int CAMERA_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        titleInput = (EditText)findViewById(R.id.title_input);
        contentInput = (EditText)findViewById(R.id.content_input);

        btn = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void callRegister(View view){

        String titulo = titleInput.getText().toString();
        String content = contentInput.getText().toString();

        if(titulo.isEmpty() || content.isEmpty()){
            Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note();
        note.setTitle(titulo);
        note.setContent(content);
        note.setDate(new Date());
        note.setFavorite(false);

        NoteRepository.add(note);

        Toast.makeText(this, "Nota registrada correctamente", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);

        finish();
    }

    public void btnClick(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK ){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagen = stream.toByteArray();
        }
    }
}
