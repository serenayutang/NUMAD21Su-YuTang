package neu.madcourse.numad21su_yutang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.net.URL;

public class LinkCollectorActivity extends AppCompatActivity {
    private ArrayList<LinkCard> linkList = new ArrayList<>();
    private RecyclerView.LayoutManager rLayoutManger;
    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;
    private FloatingActionButton addFloatButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        init(savedInstanceState);

        addFloatButton = findViewById(R.id.addButton);
        addFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                addLink(position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollectorActivity.this, "Link Deleted",
                        Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                linkList.remove(position);
                rviewAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = linkList == null ? 0 : linkList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", linkList.get(i).getLinkName());
            outState.putString(KEY_OF_INSTANCE + i + "1", linkList.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void initialItemData(Bundle savedInstanceState) {
        // not the first time opening this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (linkList == null || linkList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for (int i = 0; i < size; i++) {
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    LinkCard itemCard = new LinkCard(linkName, url);
                    linkList.add(itemCard);
                }
            }
            // First time opening this Activity
        } else {
            // list of links is initially empty
        }
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(linkList);
        LinkClickListener linkClickListener = new LinkClickListener() {
            @Override
            public void onItemClick(String url) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(addHttp(url)));
                startActivity(webIntent);
            }
        };
        rviewAdapter.setOnItemClickListener(linkClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    private Boolean domainCheck(String url) {
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String addHttp(String url) {
        if (!url.startsWith("http://") || !url.startsWith("https://")) {
            url = "https://" + url;
        }
        return url;
    }

    private void addLink(int position) {
        AlertDialog.Builder newLinkBuilder = new AlertDialog.Builder(this);
        newLinkBuilder.setTitle("Add a New Link");

        View newLinkView = LayoutInflater.from(this).inflate(R.layout.link_collector_input,
                findViewById(R.id.content), false);

        final EditText linkName = newLinkView.findViewById(R.id.urlName);
        final EditText url = newLinkView.findViewById(R.id.urlInput);
        newLinkBuilder.setView(newLinkView);

        newLinkBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String name = linkName.getText().toString();
                String urlString = url.getText().toString();
                if (domainCheck(addHttp(urlString))) {
                    linkList.add(position, new LinkCard(name, urlString));
                    Toast.makeText(LinkCollectorActivity.this, "Link Added",
                            Toast.LENGTH_LONG).show();
                    rviewAdapter.notifyItemInserted(position);
                } else {
                    Toast.makeText(LinkCollectorActivity.this, "Invalid URL",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        newLinkBuilder.show();
    }
}
