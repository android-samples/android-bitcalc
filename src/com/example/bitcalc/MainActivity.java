package com.example.bitcalc;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {
	EditText[] mEditTexts = new EditText[3]; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// EditText取得
		mEditTexts[0] = (EditText)findViewById(R.id.editText1);
		mEditTexts[1] = (EditText)findViewById(R.id.editText2);
		mEditTexts[2] = (EditText)findViewById(R.id.editText3);
		
		// 入力制限
		InputFilter filter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				for(int i = start; i < end; i++){
					char c = source.charAt(i);
					if(c == '0' || c == '1'){
						// 許可
					}
					else{
						return ""; // 非許可
					}
				}
				return null; // 許可
			}
		};
		for(int i = 0; i < 2; i++){
			//mEditTexts[i].setFilters(new InputFilter[]{filter});
		}
	}
	
	// INVERT
	public void invert(View button){
		int id = button.getId();
		// 対象の決定
		int index = 0;
		if(id == R.id.buttonInvert1)index = 0;
		else index = 1;
		// 値の取得
		String s = mEditTexts[index].getText().toString();
		int keta = s.length();
		if(keta == 0)keta = 1;
		keta = 8; // 8桁に固定しておく (確認のため)
		// 2進数扱いで数値に変換
		int n = 0;
		try{
			n = Integer.parseInt(s, 2);
		}
		catch(Exception ex){
		}
		// 反転
		n = ~n;
		// 表示変更
		mEditTexts[index].setText(binaryText(n, keta));
	}
	
	// OR, AND, XOR
	public void calc(View _button){
		// 演算の種類取得 -> op
		Button button = (Button)_button;
		String op = button.getText().toString();
		// 2値取得 -> values
		int[] values = new int[3];
		int keta = 1;
		for(int i = 0; i < 2; i++){
			String s = mEditTexts[i].getText().toString(); // 2進数扱いで数値に変換
			if(s.length() > keta){
				keta = s.length();
			}
			try{
				values[i] = Integer.parseInt(s, 2);
			}
			catch(Exception ex){
			}
		}
		keta = 8; // 8桁に固定しておく (確認のため)
		// 演算
		if(op.equals("OR")){
			values[2] = values[0] | values[1];
		}
		else if(op.equals("AND")){
			values[2] = values[0] & values[1];
		}
		else if(op.equals("XOR")){
			values[2] = values[0] ^ values[1];
		}
		// 結果表示
		for(int i = 0; i < 3; i++){
			String s = binaryText(values[i], keta);
			mEditTexts[i].setText(s);
		}
	}
	
	// COPY
	public void copy(View button){
		String s = mEditTexts[2].getText().toString();
		mEditTexts[0].setText(s);
	}
	
	public String binaryText(int n, int keta){
		String s = String.format("%" + keta + "s", Integer.toBinaryString(n)).replace(' ', '0');
		if(s.length() > keta){
			s = s.substring(s.length() - keta);
		}
		return s;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
