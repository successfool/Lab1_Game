package com.example.alab1_sem2_artem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    ArrayList<Item> items = new ArrayList<Item>();
    String FILE_NAME = "image";
    String FILE_EX = ".png";
    int pos1 = -1, pos2 = -1, pos3 = -1, pos4 = -1;
    private static final int REQUEST_PERMISSION_WRITE = 1001;
    private boolean permissionGranted;
    Button RestartButton;
    ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RestartButton = (Button) findViewById(R.id.Restart);
        Image = (ImageView) findViewById(R.id.imageView4);

        recyclerView = findViewById(R.id.Recycler);
        recyclerView.setLayoutManager(new CustomGridLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        RestartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                items.clear();
                GameItSelf();
            }
        });

        Image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RestartButton.setVisibility(recyclerView.INVISIBLE);
                Image.setVisibility(recyclerView.INVISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.fr_place, new BigPicFragment()).commit();
            }
        });

        GameItSelf();
        CanTouch();
    }

    public void ShowButton()
    {
        RestartButton.setVisibility(recyclerView.VISIBLE);
        Image.setVisibility(recyclerView.VISIBLE);
    }

    public void EndGame()
    {
        items.clear();
        GameItSelf();
        CanTouch();
    }

    void GameItSelf()
    {
        int field[][] = new int[3][3];
        Random r = new Random();
        List<Integer> generated = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                while (true)
                {
                    int rand = (r.nextInt(9) + 1);
                    if(!generated.contains(rand))
                    {
                        generated.add(rand);
                        field[i][j] = rand;
                        //if(rand == 9)
                            //ImagePls(17);
                        //else
                        ImagePls(rand);
                        break;
                    }
                }
            }
        }
    }

    void GameFinish()
    {
        for(int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getNumber() != i)
            {
                Log.d("Igra", "Ne Zakonchena");
                break;
            }
            if (i == 8)
            {
                Log.d("Igra", "Zakonchena");

                RestartButton.setVisibility(recyclerView.INVISIBLE);
                Image.setVisibility(recyclerView.INVISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.fr_place, new FinishFragment()).commit();
            }
        }
    }

    void AddItem(Uri pic, int i)
    {
        //if (i == 17) i = 9;
        items.add(new Item(pic, i - 1));
        ItemAdapter itemAdapter = new ItemAdapter(items);
        recyclerView.setAdapter(itemAdapter);
    }

    void ImagePls(int i)
    {
        Uri NP;
        File file = new File("/data/data/com.example.alab1_sem2_artem/files/"+ FILE_NAME + i + FILE_EX);
        if (file.exists() == true)
        {
            NP = Uri.parse("/data/data/com.example.alab1_sem2_artem/files/"+ FILE_NAME + i + FILE_EX);
            AddItem(NP, i);
        }
        else Log.d("Image", "Not Found");
    }

    void CanTouch()
    {
        int ThirdElement = -1;
        for(int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getNumber() == 8)
            {
                ThirdElement = i;
                break;
            }
        }
        if (ThirdElement != -1)
        {
            try
            {
                pos1 = items.get(ThirdElement - 1).getNumber();
            }
            catch (Throwable ex)
            {
                Log.d("Ploho Delo",ex.toString());
                pos1 = -1;
            }
            try
            {
                pos2 = items.get(ThirdElement + 1).getNumber();
            }
            catch (Throwable ex)
            {
                Log.d("Ploho Delo",ex.toString());
                pos2 = -1;
            }
            try
            {
                pos3 = items.get(ThirdElement - 3).getNumber();
            }
            catch (Throwable ex)
            {
                Log.d("Ploho Delo",ex.toString());
                pos3 = -1;
            }
            try
            {
                pos4 = items.get(ThirdElement + 3).getNumber();
            }
            catch (Throwable ex)
            {
                Log.d("Ploho Delo",ex.toString());
                pos4 = -1;
            }
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN)

    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
        {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            CanTouch();
            if ((items.get(fromPosition).getNumber() == pos1 || items.get(fromPosition).getNumber() == pos2 || items.get(fromPosition).getNumber() == pos3 || items.get(fromPosition).getNumber() == pos4) && items.get(toPosition).getNumber() == 8)
            {
                if (items.get(fromPosition).getNumber() == pos1 && toPosition != 3 && toPosition != 6)
                {
                    Item Ia = new Item(items.get(fromPosition).getPic(), items.get(fromPosition).getNumber());
                    items.set(fromPosition, items.get(toPosition));
                    items.set(toPosition, Ia);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    GameFinish();
                    CanTouch();
                }
                if (items.get(fromPosition).getNumber() == pos2 && toPosition != 2 && toPosition != 5)
                {
                    Item Ia = new Item(items.get(fromPosition).getPic(), items.get(fromPosition).getNumber());
                    items.set(fromPosition, items.get(toPosition));
                    items.set(toPosition, Ia);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    GameFinish();
                    CanTouch();
                }
                if (items.get(fromPosition).getNumber() == pos3 || items.get(fromPosition).getNumber() == pos4)
                {
                    Item Ia = new Item(items.get(toPosition).getPic(), items.get(toPosition).getNumber());
                    items.set(toPosition, items.get(fromPosition));
                    items.set(fromPosition, Ia);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    GameFinish();
                    CanTouch();
                }
            }
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
        {
            int position = viewHolder.getAdapterPosition();
            CanTouch();
            switch (direction)
            {
                case ItemTouchHelper.LEFT:
                    if (items.get(position).getNumber() == pos2 && position != 3 && position != 6)
                    {
                        Item Ia = new Item(items.get(position).getPic(), items.get(position).getNumber());
                        items.set(position, items.get(position - 1));
                        items.set(position - 1, Ia);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        GameFinish();
                    }
                    break;
                case ItemTouchHelper.RIGHT:
                    if (items.get(position).getNumber() == pos1 && position != 2 && position != 5)
                    {
                        Item Ia2 = new Item(items.get(position).getPic(), items.get(position).getNumber());
                        items.set(position, items.get(position + 1));
                        items.set(position + 1, Ia2);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        GameFinish();
                    }
                    break;
                case ItemTouchHelper.UP:
                    if (items.get(position).getNumber() == pos4)
                    {
                        Item Ia3 = new Item(items.get(position).getPic(), items.get(position).getNumber());
                        items.set(position, items.get(position - 3));
                        items.set(position - 3, Ia3);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        GameFinish();
                    }
                    break;
                case ItemTouchHelper.DOWN:
                    if (items.get(position).getNumber() == pos3)
                    {
                        Item Ia4 = new Item(items.get(position).getPic(), items.get(position).getNumber());
                        items.set(position, items.get(position + 3));
                        items.set(position + 3, Ia4);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        GameFinish();
                    }
                    break;
            }
            recyclerView.getAdapter().notifyDataSetChanged();
            CanTouch();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_WRITE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissionGranted = true;
                    Toast.makeText(this, "Разрешения получены", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Необходимо дать разрешения", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
