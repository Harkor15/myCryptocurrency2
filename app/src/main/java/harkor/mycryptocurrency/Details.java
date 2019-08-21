package harkor.mycryptocurrency;

import android.os.Parcel;
import android.os.Parcelable;

public class Details implements Parcelable {
    public final String name;
    public final String date;

    public Details(String name, String date) {
        this.name = name;
        this.date = date;
    }


    protected Details(Parcel in) {
        name = in.readString();
        date = in.readString();
    }

    public static final Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
