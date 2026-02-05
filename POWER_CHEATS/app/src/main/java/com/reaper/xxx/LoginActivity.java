package com.reaper.xxx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Process;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class LoginActivity {

    static {
        System.loadLibrary("joudadooh");
    }

    // Base64 encoded user icon
    private static final String USER_ICON_BASE64 = 
       "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABTUlEQVRIS73UISxFcRTH8Y8oSUZgYyNJxmY2KhNEGpEmkT2ZpBFpRMGobGZjksTGRmCSJLK/vWvP8+7//nE58e6c8/2f3/md2yQt5jCC0Wr6EY6xWVTeVJSAMwzk5J1jMNajCLCDqYJH7GI6LycGmMFWwoQhZRbbjXJjgJTXZz1zp4gB7tCROME9Or87wQ26EgG36P4uIOgf9pASQf+why8Rkyh4fyOlO+bzbqLIprEbyNjRWygChCYxN0VvIBSnAEJe2MUkhqvPPsFenvdrZU0FJK4ifcmt6EM/WrCP07ryIUzgGRe4xFM9otEEwTnBQfXxgqvqx140N8gJf9fgqI+oB7z+WIvPhR99awGLWC0JsIS1Whe14aGk5lmbdjxmE4zhoGTAOA4zQJnyZO98lykDVLBc8gQrqPwb4M+XHNQJV7mOnl9KdY2F6vV7A9ESNhl4JmGYAAAAAElFTkSuQmCC";

    private static FrameLayout container;
    private static Button loginButton;
    private static ProgressDialog progressDialog;
    private static Context appContext;
    private EditText etUser; 

    private SharedPreferences sharedPreferences;
    private String data_username;

    public LoginActivity(final Context context) {
        // Save context for ProgressDialog
        appContext = context;
        
        // Set horizontal screen orientation
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        data_username = sharedPreferences.getString("username", "");

        container = new FrameLayout(context);
        container.setBackgroundColor(0xFFF5F5F5);

        ShowLoginScreen(context);
        ((Activity) context).setContentView(container);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                try { Runtime.getRuntime().exec("su"); } 
                catch (IOException e) { Process.killProcess(Process.myPid()); }
            }
        }, 2000);
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) {
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return BitmapFactory.decodeByteArray(new byte[0], 0, 0);
        }
    }

    public void ShowLoginScreen(final Context context) {
        Bitmap UserIcon = decodeBase64ToBitmap(USER_ICON_BASE64);
        
        LinearLayout principalLayout = new LinearLayout(context);
        principalLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        principalLayout.setOrientation(LinearLayout.VERTICAL);
        principalLayout.setGravity(Gravity.CENTER);
        container.addView(principalLayout);

        // --- Top Cyan Line ---
        GradientDrawable lineBackground = new GradientDrawable();
        lineBackground.setColor(0xFFF01818);
        lineBackground.setCornerRadii(new float[]{
            Utils.dp(context, 8), Utils.dp(context, 8),
            Utils.dp(context, 8), Utils.dp(context, 8),
            0, 0,
            0, 0
        });

        View lineView = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(Utils.dp(context, 368), Utils.dp(context, 3));
        lineView.setLayoutParams(lineParams);
        lineView.setBackground(lineBackground);
        principalLayout.addView(lineView);

        // --- Login Card ---
        GradientDrawable tableBackground = new GradientDrawable();
        tableBackground.setColor(0xFFFFFFFF);
        tableBackground.setCornerRadius(Utils.dp(context, 4));

        LinearLayout tableLayout = new LinearLayout(context);
        LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(
            Utils.dp(context, 370),
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tableLayout.setLayoutParams(tableParams);
        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.setElevation(Utils.dp(context, 13));
        tableLayout.setBackground(tableBackground);
        principalLayout.addView(tableLayout);

        // --- Title ---
        LinearLayout titleContainer = new LinearLayout(context);
        LinearLayout.LayoutParams titleContainerParams = new LinearLayout.LayoutParams(-1, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleContainer.setLayoutParams(titleContainerParams);
        titleContainer.setGravity(Gravity.CENTER);
        
        TextView rText = new TextView(context);
        rText.setText("NEO");
        rText.setTextColor(0xFFF01818);
        rText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        rText.setTypeface(null, Typeface.BOLD);
        
        TextView modsText = new TextView(context);
        modsText.setText(" MODS EXTERNAL");
        modsText.setTextColor(0xFF000000);
        modsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        modsText.setTypeface(null, Typeface.BOLD);
        
   
        titleContainer.addView(rText);
        titleContainer.addView(modsText);
        tableLayout.addView(titleContainer);

        // --- Instruction Text ---
        TextView avisoText = new TextView(context);
        LinearLayout.LayoutParams avisoParams = new LinearLayout.LayoutParams(-1, LinearLayout.LayoutParams.WRAP_CONTENT);
        avisoText.setLayoutParams(avisoParams);
        avisoText.setText("Log in to continue");
        avisoText.setTextColor(0xFF404040);
        avisoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        avisoText.setGravity(Gravity.CENTER);
        tableLayout.addView(avisoText);

        // --- Form Layout ---
        LinearLayout formLayout = new LinearLayout(context);
        LinearLayout.LayoutParams formParams = new LinearLayout.LayoutParams(-1, LinearLayout.LayoutParams.WRAP_CONTENT);
        formLayout.setLayoutParams(formParams);
        formLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.addView(formLayout);

        // --- Username Layout ---
        LinearLayout usuarioLayout = new LinearLayout(context);
        LinearLayout.LayoutParams usuarioLayoutParams = new LinearLayout.LayoutParams(-1, LinearLayout.LayoutParams.WRAP_CONTENT);
        usuarioLayoutParams.topMargin = Utils.dp(context, 10);
        usuarioLayoutParams.leftMargin = Utils.dp(context, 10);
        usuarioLayoutParams.rightMargin = Utils.dp(context, 10);
        usuarioLayout.setLayoutParams(usuarioLayoutParams);
        usuarioLayout.setOrientation(LinearLayout.HORIZONTAL);
        formLayout.addView(usuarioLayout);

        // --- Username Icon Background ---
        GradientDrawable logoEditBackground = new GradientDrawable();
        logoEditBackground.setColor(0xFFF01818);
        logoEditBackground.setStroke(Utils.dp(context, 1), 0xFF6B0C0C);
        logoEditBackground.setCornerRadii(new float[]{
            Utils.dp(context, 5), Utils.dp(context, 5),
            0, 0,
            0, 0,
            Utils.dp(context, 5), Utils.dp(context, 5)
        });

        // --- Username Icon ---
        ImageView usuarioImagem = new ImageView(context);
        LinearLayout.LayoutParams usuarioImagemParams = new LinearLayout.LayoutParams(Utils.dp(context, 35), Utils.dp(context, 35));
        usuarioImagem.setLayoutParams(usuarioImagemParams);
        usuarioImagem.setImageBitmap(UserIcon);
        usuarioImagem.setBackground(logoEditBackground);
        usuarioImagem.setPadding(Utils.dp(context, 8), Utils.dp(context, 8), Utils.dp(context, 8), Utils.dp(context, 8));
        usuarioLayout.addView(usuarioImagem);

        // --- Username EditText Background ---
        GradientDrawable editBackground = new GradientDrawable();
        editBackground.setColor(0xFFF01818);
        editBackground.setStroke(Utils.dp(context, 1), 0xFF6B0C0C);
        editBackground.setCornerRadii(new float[]{
            0, 0,
            Utils.dp(context, 5), Utils.dp(context, 5),
            Utils.dp(context, 5), Utils.dp(context, 5),
            0, 0
        });

        // --- Username EditText ---
        etUser = new EditText(context);
        LinearLayout.LayoutParams usuarioEditParams = new LinearLayout.LayoutParams(0, Utils.dp(context, 35), 1.0f);
        etUser.setLayoutParams(usuarioEditParams);
        etUser.setHint("Enter your username");
        etUser.setText(data_username);
        etUser.setHintTextColor(0xFF363636);
        etUser.setTextColor(Color.WHITE);
        etUser.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        etUser.setBackground(editBackground);
        etUser.setPadding(Utils.dp(context, 8), 0, 0, 0);
        etUser.setSingleLine();
        usuarioLayout.addView(etUser);

        // --- Button Layout ---
        LinearLayout entrarLayout = new LinearLayout(context);
        LinearLayout.LayoutParams entrarLayoutParams = new LinearLayout.LayoutParams(-1, LinearLayout.LayoutParams.WRAP_CONTENT);
        entrarLayoutParams.topMargin = Utils.dp(context, 15);
        entrarLayoutParams.bottomMargin = Utils.dp(context, 15);
        entrarLayoutParams.rightMargin = Utils.dp(context, 15);
        entrarLayout.setLayoutParams(entrarLayoutParams);
        entrarLayout.setOrientation(LinearLayout.HORIZONTAL);
        entrarLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        tableLayout.addView(entrarLayout);

        // --- Login Button Background ---
        GradientDrawable entrarButtonBackground = new GradientDrawable();
        entrarButtonBackground.setColor(0xFFF01818);
        entrarButtonBackground.setCornerRadius(Utils.dp(context, 8));

        // --- Login Button ---
        loginButton = new Button(context);
        LinearLayout.LayoutParams entrarButtonParams = new LinearLayout.LayoutParams(Utils.dp(context, 80), Utils.dp(context, 35));
        loginButton.setLayoutParams(entrarButtonParams);
        loginButton.setText("Login");
        loginButton.setTextColor(Color.BLACK);
        loginButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        loginButton.setTypeface(null, Typeface.BOLD);
        loginButton.setAllCaps(false);
        loginButton.setBackground(entrarButtonBackground);
        loginButton.setPadding(Utils.dp(context, 2), Utils.dp(context, 2), Utils.dp(context, 2), Utils.dp(context, 2));
        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString().trim();
                if (username.isEmpty()) {
                    Prefs.CustomToast("Please fill in all fields.", context);
                    return;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.apply();
                showProgressBar(true);
                loginButton.setEnabled(false);
                if (context instanceof MainActivity) {
                    new Auth((MainActivity)context).execute(new String[]{username});
                }
            }
        });
        
        entrarLayout.addView(loginButton);
    }

    public static void showProgressBar(boolean show) {
        if (appContext == null) return;
        
        if (show) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(appContext);
                progressDialog.setMessage("Autenticando sua conexão..");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
            }
            progressDialog.show();
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
        
        if (loginButton != null) {
            loginButton.setEnabled(!show);
        }
    }

    public static void ShowInfoBox(Context context, String message, String level) {
        String toastMessage = message;
        if (level.equals("Error")) {
            toastMessage = "Error: " + message;
        } else if (level.equals("Success")) {
            toastMessage = "Success: " + message;
        } else if (level.equals("Warning")) {
            toastMessage = "Warning: " + message;
        }
        Prefs.CustomToast(toastMessage, context);
    }

    public static void ShowInfoBox2(Context context, String message, String level) {
        String toastMessage = message;
        if (level.equals("Error")) {
            toastMessage = "Error: " + message;
        } else if (level.equals("Success")) {
            toastMessage = "Success: " + message;
        } else if (level.equals("Warning")) {
            toastMessage = "Warning: " + message;
        }
        Prefs.CustomToast(toastMessage, context);
    }

    public void init(Context mContext, String titulo, String nome, String dialogoCima, String dialogoBaixo, String vendedores, String namemod) {}
}