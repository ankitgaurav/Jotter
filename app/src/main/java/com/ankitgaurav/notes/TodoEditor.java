package com.ankitgaurav.notes;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoEditor extends AppCompatActivity {
    EditText editText;
    AppDBHandler todosDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_editor);
        todosDBHandler = new AppDBHandler(this);
        editText = (EditText) findViewById(R.id.editTextTodo);
        showKeyboard(true);
    }

    public void clickAddTodo(View view){
        String todoText = editText.getText().toString().trim();
        if (todoText.equals("")) {//Empty note discarded
            return ;
        }
        else{
            Todo todo = new Todo();
            todo.setTodoText(todoText);
            todo.setCreatedAt(getDateTime());
            todosDBHandler.addTodo(todo);

        }
        //returnHome();
        finish();
    }
    private void returnHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void showKeyboard(boolean state){
        if(state){
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
            );
        }
        else{
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            );
        }
    }
}
