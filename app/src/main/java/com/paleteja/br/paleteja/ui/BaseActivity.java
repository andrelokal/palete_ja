package com.paleteja.br.paleteja.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.api.ApiClient;
import com.paleteja.br.paleteja.ui.activity.CompradorOfertasActivity;
import com.paleteja.br.paleteja.ui.activity.FiltrarUsuariosPaletesActivity;
import com.paleteja.br.paleteja.ui.activity.HomeCompradorActivity;
import com.paleteja.br.paleteja.ui.activity.HomeVendedorActivity;
import com.paleteja.br.paleteja.ui.activity.PaletesVendedorActivity;
import com.paleteja.br.paleteja.ui.activity.LoginActivity;
import com.paleteja.br.paleteja.ui.activity.PerfilActivity;

public class BaseActivity extends AppCompatActivity {

    public ApiClient api;
    public Toolbar toolbar;
    public ImageView searchToolbar;

    public static Dialog dialog;

    public BaseActivity(){
        api = new ApiClient();
    }

    /*--------------------------------------------------
            Toolbar customizada
    --------------------------------------------------*/

    public void setMenuToolbar(boolean menu) {

        toolbar = findViewById(R.id.toolbar);
//        searchToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu_gallery);

        if(menu) {

            final DrawerLayout drawer = findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_menu_left);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            int id = menuItem.getItemId();

                            //vendedor
//                            if (id == R.id.nav_minhas_ofertas) {
//                                Intent intent = new Intent(BaseActivity.this, VendedorOfertasActivity.class);
//                                startActivity(intent);
//                            } else

//                            if (id == R.id.nav_home) {
//                                Intent intent = new Intent(BaseActivity.this, HomeVendedorActivity.class);
//                                startActivity(intent);
//                            } else

                                if (id == R.id.nav_meus_paletes) {
                                Intent intent = new Intent(BaseActivity.this, PaletesVendedorActivity.class);
                                startActivity(intent);
                            }
//                            else if (id == R.id.nav_ofertas_avulsas) {
//                                Intent intent = new Intent(BaseActivity.this, OfertasAvulsaActivity.class);
//                                startActivity(intent);
//                            }
                            //perfil user
                            else if (id == R.id.nav_meu_perfil) {
                                Intent intent = new Intent(BaseActivity.this, PerfilActivity.class);
                                startActivity(intent);
                            }

                            //comprador
                            else if (id == R.id.nav_ofertas_comprador) {
                                Intent intent = new Intent(BaseActivity.this, CompradorOfertasActivity.class);
                                startActivity(intent);
                            } else if (id == R.id.nav_home_comprador) {
                                Intent intent = new Intent(BaseActivity.this, HomeCompradorActivity.class);
                                startActivity(intent);
                            }


                            //perfil user Comrpador
                            else if (id == R.id.nav_meu_perfil) {
                                Intent intent = new Intent(BaseActivity.this, PerfilActivity.class);
                                startActivity(intent);
                            }

                            //sair
                            else if (id == R.id.nav_sair) {
                                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }

                            DrawerLayout drawer = findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            return true;
                        }
                    });

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(BaseActivity.this, FiltrarUsuariosPaletesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    public void showMessage(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public void showLoad(){


        //Context context = getContext();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        ProgressBar spinner = new android.widget.ProgressBar( this, null, android.R.attr.progressBarStyle);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        dialog.show();
    }

    public static void hideLoad(){

        if(dialog != null){
            dialog.dismiss();
        }

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_minhas_ofertas) {
//            showMessage("Teste 1");
//
//        } else if (id == R.id.nav_sair) {
//            showMessage("Teste 2");
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
