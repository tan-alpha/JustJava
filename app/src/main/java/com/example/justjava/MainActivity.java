package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=2;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        TextView nameOfPerson= (TextView) findViewById(R.id.name);
        String name = nameOfPerson.getText().toString();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int basePrice=5;
        if(hasChocolate)
            basePrice = basePrice +2;
        if(hasWhippedCream)
            basePrice = basePrice+1;
        int price=calculatePrice(basePrice);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if(quantity==100)
            Toast.makeText(getApplicationContext(),"Cannot be more than 100",
                    Toast.LENGTH_SHORT).show();
        else{
            quantity=quantity+1;
            display(quantity);
        }
    }

    public void decrement(View view) {
        if(quantity==1)
            Toast.makeText(getApplicationContext(),"Cannot be 0",
                    Toast.LENGTH_SHORT).show();
        else{
            quantity=quantity-1;
            display(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private int calculatePrice(int basePrice){
        return quantity*basePrice;
    }

    private String createOrderSummary(int price,
                                      boolean addWhippedCream,
                                      boolean addChocolate,
                                      String name){
        return "Name: " + name + "\nAdd Whipped Cream? "+ addWhippedCream + "\nAdd Chocolate? "
                + addChocolate + "\nQuantity: "+ quantity +"\nTotal: Rs. " + price + "\nThank You!";
    }
}
