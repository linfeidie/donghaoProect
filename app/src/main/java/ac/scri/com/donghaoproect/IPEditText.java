package ac.scri.com.donghaoproect;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 文件描述：.
 * <p>
 * 作者：Created by 林飞堞 on 2019/10/23
 * <p>
 * 版本号：donghaoProect
 */
public class IPEditText extends LinearLayout {

    private AppCompatEditText mIP1;
    private EditText mIP2;
    private EditText mIP3;
    private EditText mIP4;
    private EditText currentFocusIpEditText;

    public IPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 初始化控件
         */
        LayoutInflater.from(context).inflate(R.layout.ip_edittext, this);
        mIP1 = (AppCompatEditText) findViewById(R.id.ip_1);
        mIP2 = (EditText) findViewById(R.id.ip_2);
        mIP3 = (EditText) findViewById(R.id.ip_3);
        mIP4 = (EditText) findViewById(R.id.ip_4);

        mIP1.addTextChangedListener(new IpTextWatcher(null, mIP1, mIP2));
        mIP2.addTextChangedListener(new IpTextWatcher(mIP1, mIP2, mIP3));
        mIP3.addTextChangedListener(new IpTextWatcher(mIP2, mIP3, mIP4));
        mIP4.addTextChangedListener(new IpTextWatcher(mIP3, mIP4, null));

        mIP1.setOnClickListener(new IpOnClickListener());
        mIP2.setOnClickListener(new IpOnClickListener());
        mIP3.setOnClickListener(new IpOnClickListener());
        mIP4.setOnClickListener(new IpOnClickListener());
    }

    public String getIpAddress() {
        String strIp1 = mIP1.getText().toString().trim();
        String strIp2 = mIP2.getText().toString().trim();
        String strIp3 = mIP3.getText().toString().trim();
        String strIp4 = mIP4.getText().toString().trim();
        return ((strIp1.equals("")) ? "0" : strIp1) + "." + ((strIp2.equals("")) ? "0" : strIp2) + "." + ((strIp3.equals("")) ? "0" : strIp3) + "."
                + ((strIp4.equals("")) ? "0" : strIp4);
    }

    public String getText() {
        return getIpAddress();
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        mIP1.setEnabled(b);
        mIP2.setEnabled(b);
        mIP3.setEnabled(b);
        mIP4.setEnabled(b);
    }

    /**
     *
     * @param strIpAddress 如：192.168.1.1
     */
    public void setIpAddress(String strIpAddress) {
        if ((strIpAddress != null) && (strIpAddress.equals(""))) {
            mIP1.setText("");
            mIP2.setText("");
            mIP3.setText("");
            mIP4.setText("");
        } else {
            if ((strIpAddress == null) || (strIpAddress.indexOf(".") == -1) || (strIpAddress.split("\\.").length != 4)) {
                throw new UnsupportedOperationException("Invalid IP Address:" + strIpAddress);
            }

            String[] strIp = strIpAddress.split("\\.");
            mIP1.setText(strIp[0]);
            mIP2.setText(strIp[1]);
            mIP3.setText(strIp[2]);
            mIP4.setText(strIp[3]);
        }
        setFocus(mIP4, currentFocusIpEditText);
        mIP4.setSelection(mIP4.getText().length());
    }

    private void setFocus(EditText focus, EditText noFocus) {
        if (noFocus != null) {
            noFocus.setFocusable(false);
            noFocus.setCursorVisible(false);
            noFocus.setFocusableInTouchMode(false);
            noFocus.clearFocus();
        }

        focus.setFocusable(true);
        focus.setCursorVisible(true);
        focus.setFocusableInTouchMode(true);
        focus.requestFocus();

        currentFocusIpEditText = focus;
    }

    public class IpTextWatcher implements TextWatcher {
        EditText leftEditText;
        EditText currentEditText;
        EditText rightEditText;

        public IpTextWatcher(EditText leftEditText, EditText currentEditText, EditText rightEditText) {
            this.leftEditText = leftEditText;
            this.currentEditText = currentEditText;
            this.rightEditText = rightEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (currentEditText != null) {
                currentEditText.setFocusable(true);
                currentEditText.requestFocus();
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                if (leftEditText != null) {
                    setFocus(leftEditText, currentEditText);
                    leftEditText.setSelection(leftEditText.getText().length());
                }
                return;
            }

            String str = s.toString().trim();
            int periodIndex = str.indexOf(".");
            if (periodIndex > -1) {
                if (rightEditText != null) {
                    setFocus(rightEditText, currentEditText);
                    rightEditText.setSelection(0, rightEditText.getText().length());
                }

                s.delete(periodIndex, periodIndex + 1);
                return;
            }
            if (Integer.parseInt(str) > 255) {
                s.delete(s.length() - 1, s.length());
                return;
            }
            if (str.startsWith("0")) {
                if (rightEditText != null) {
                    setFocus(rightEditText, currentEditText);
                    rightEditText.setSelection(0, rightEditText.getText().length());
                }
                return;
            }
            if (s.length() == 3) {
                if (rightEditText != null) {
                    setFocus(rightEditText, currentEditText);
                    rightEditText.setSelection(0, rightEditText.getText().length());
                }
            }
        }
    }

    public class IpOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v instanceof EditText) {
                EditText temp = (EditText) v;
                if (!temp.hasFocus()) {
                    setFocus(temp, currentFocusIpEditText);
                }
            }
        }
    }
}
