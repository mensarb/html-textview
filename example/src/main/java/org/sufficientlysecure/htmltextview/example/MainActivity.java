/*
 * Copyright (C) 2013-2016 Dominik Schürmann <dominik@schuermann.eu>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sufficientlysecure.htmltextview.example;

import static org.sufficientlysecure.htmltextview.example.WebViewActivity.EXTRA_TABLE_HTML;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.sufficientlysecure.htmltextview.spannable.ClickableTableSpan;
import org.sufficientlysecure.htmltextview.spannable.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.image.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.view.HtmlTextView;

public class MainActivity extends AppCompatActivity {

    // The html table(s) are individually passed through to the ClickableTableSpan implementation
    // presumably for a WebView activity.
    class ClickableTableSpanImpl extends ClickableTableSpan {
        @NonNull
        @Override
        public ClickableTableSpan newInstance() {
            return new ClickableTableSpanImpl();
        }

        @Override
        public void onClick(@NonNull View widget) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra(EXTRA_TABLE_HTML, getTableHtml());
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HtmlTextView textView = (HtmlTextView) findViewById(R.id.html_text);

        //text.setRemoveFromHtmlSpace(false); // default is true
        textView.setClickableTableSpan(new ClickableTableSpanImpl());
        DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
        drawTableLinkSpan.setTableLinkText("[tap for table]");
        textView.setDrawTableLinkSpan(drawTableLinkSpan);

        // Best to use indentation that matches screen density.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        textView.setListIndentPx(metrics.density * 10);

        // a tag click listener
        textView.setOnClickATagListener((widget, spannedText, href) -> {
            final Toast toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
            toast.setText(href);
            toast.show();

            return false;
        });
        textView.blockQuoteBackgroundColor = getResources().getColor(R.color.whitish);
        textView.blockQuoteStripColor = getResources().getColor(R.color.blue);

        textView.setHtml(R.raw.example, new HtmlResImageGetter(getBaseContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_data_binding) {
            startActivity(new Intent(this, DataBindingExampleActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
