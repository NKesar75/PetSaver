package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static int petNumber;

    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    private List<PetInfo> petList = new ArrayList<>();

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();
        gridView = (GridView) findViewById(R.id.mygridview);
        gridView.setOnItemClickListener(onItemClick);

        draw = (DrawerLayout) findViewById(R.id.activity_favorite);
        toggle = new ActionBarDrawerToggle(this, draw, R.string.open, R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            petNumber = petList.get(position).getPetnumber();
            //startActivity(new Intent(Home.this, Description.class));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                return true;
            case R.id.nav_Search:
                return true;
            case R.id.nav_favorite:
                return true;
            case R.id.nav_logout:
                return true;
        }
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_home);
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            startActivity(new Intent(Favorite.this,Home.class));
        }
        else if(id==R.id.nav_Search)
        {
            startActivity(new Intent(Favorite.this,search.class));
        }
        else if(id == R.id.nav_favorite)
        {
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_favorite);
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else if(id== R.id.nav_logout)
        {
            //startActivity(new Intent(Home.this,Profile.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_favorite);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
