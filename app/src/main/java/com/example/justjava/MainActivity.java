/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {

        if (quantity == 20){
            Toast.makeText(this,"Sorry, the maximum limit to order is 20 cups",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1){
            Toast.makeText(this,"Sorry, you have to order atleast 1 cup",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateToppingCheckBox = (CheckBox) findViewById(R.id.chocolate_topping_check_box);
        boolean hasChocolateTopping = chocolateToppingCheckBox.isChecked();

        EditText customerNameEditText = (EditText) findViewById(R.id.customer_name);
        String customerName = customerNameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream,hasChocolateTopping);

        String priceMessage = createOrderSummary(customerName,price,hasWhippedCream,hasChocolateTopping);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "mohammedfasilp12345@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "coffee order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) == null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @para quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolateTopping) {
        int basePrice = 10;
        if (hasWhippedCream){
            basePrice += 5;
        }
        if (hasChocolateTopping){
            basePrice += 5;
        }
        int totalPrice = quantity * basePrice;
        return totalPrice;
    }

    /**
     * display all the details entered by the user
     */

    private String createOrderSummary(String name,int price,boolean hasWhippedCream,boolean hasChocolateTopping){
        String message = getString(R.string.order_summary_name, name);
        message += "\nAdd Whipped Cream : " + hasWhippedCream;
        message += "\nAdd Chocolate Topping : " + hasChocolateTopping;
        message += "\nQuantity : " + quantity;
        message += "\nTotal : " + price + "\\-";
        message += "\n" + getString(R.string.thanks);

        return message;
    }
}