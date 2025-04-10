package com.example.lab_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String rideId;
    private String currentUser;
    private UserDatabaseHelper dbHelper;
    private ChatAdapter adapter;
    private List<ChatMessage> messageList;
    private EditText inputMessage;
    private Button sendBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        // Get rideId and userId from Intent
        rideId = getIntent().getStringExtra("rideId");
        currentUser = getIntent().getStringExtra("userId");

        dbHelper = new UserDatabaseHelper(this);

        recyclerView = findViewById(R.id.messageList);
        inputMessage = findViewById(R.id.inputMessage);
        sendBtn = findViewById(R.id.sendMessageBtn);

        // Load messages
        messageList = dbHelper.getChatMessagesAsObjects(rideId);  // method must return List<ChatMessage>
        adapter = new ChatAdapter(messageList, currentUser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(v -> {
            String msg = inputMessage.getText().toString().trim();
            if (!msg.isEmpty()) {
                dbHelper.saveChatMessage(rideId, currentUser, msg);
                inputMessage.setText("");

                // Reload updated messages
                messageList.clear();
                messageList.addAll(dbHelper.getChatMessagesAsObjects(rideId));  // refresh list
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    private void refreshMessages() {
        messageList.clear();
        messageList.addAll(getFormattedMessages());
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    private List<ChatMessage> getFormattedMessages() {
        List<String> rawMessages = dbHelper.getChatMessages(rideId);
        List<ChatMessage> formattedList = new ArrayList<>();
        for (String msg : rawMessages) {
            String[] parts = msg.split(":", 2);
            if (parts.length == 2) {
                formattedList.add(new ChatMessage(parts[0].trim(), parts[1].trim()));
            }
        }
        return formattedList;
    }
}
