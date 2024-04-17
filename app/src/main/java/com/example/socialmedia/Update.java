package com.example.socialmedia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;
    private Button buttonUpdate;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize EditText and Button
        editTitle = findViewById(R.id.edit_tittle_update);
        editDescription = findViewById(R.id.edit_desc_update);
        buttonUpdate = findViewById(R.id.bt_update);

        // Receive the position of the item to be updated from the previous activity
        itemPosition = getIntent().getIntExtra("itemPosition", -1);

        // Set OnClickListener for the Update Button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated title and description
                String updatedTitle = editTitle.getText().toString();
                String updatedDescription = editDescription.getText().toString();

                // Verifica se o título foi inserido
                if (updatedTitle.isEmpty()) {
                    editTitle.setError("Insira um título");
                    return;
                }

                // Verifica se a descrição foi inserida
                if (updatedDescription.isEmpty()) {
                    editDescription.setError("Insira uma descrição");
                    return;
                }

                // Atualiza os dados na base de dados
                MyBD myBD = new MyBD(Update.this, 2); // Passa o contexto e a versão do banco de dados
                int linhasAfetadas = myBD.atualizarPost(updatedTitle, updatedDescription, itemPosition);

                if (linhasAfetadas > 0) {
                    // Se a atualização foi bem-sucedida, mostra uma mensagem Toast
                    Toast.makeText(Update.this, "Item at position " + itemPosition + " updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("UpdateActivity", "Item at position " + itemPosition + " updated successfully!");
                } else {
                    // Caso contrário, mostra uma mensagem de erro
                    Toast.makeText(Update.this, "Failed to update item at position " + itemPosition, Toast.LENGTH_SHORT).show();
                    Log.e("UpdateActivity", "Failed to update item at position " + itemPosition);
                }

                // Fecha a atividade atual
                finish();
            }
        });
    }
}
