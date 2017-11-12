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

public class info extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    return true;
                case R.id.nav_Search:
                    return true;
                case R.id.nav_favorite:
                    return true;
                case R.id.nav_logout:
                    return true;
            }
            if (toggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_search);
            if (drawerLayout.isDrawerOpen((GravityCompat.START)))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                super.onBackPressed();
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(info.this, Home.class));
            } else if (id == R.id.nav_Search) {
                startActivity(new Intent(info.this,search.class));
            } else if (id == R.id.nav_favorite) {
                startActivity(new Intent(info.this, Favorite.class));
            } else if (id == R.id.nav_logout) {


            }
            else if(id == R.id.nav_info)
            {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_search);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_search);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }