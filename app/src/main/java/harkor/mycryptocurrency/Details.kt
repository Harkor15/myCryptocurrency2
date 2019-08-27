package harkor.mycryptocurrency

import android.os.Parcel
import android.os.Parcelable

class Details : Parcelable {
    val name: String?
    val date: String?

    constructor(name: String, date: String) {
        this.name = name
        this.date = date
    }


    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        date = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
    }

    companion object {

        val CREATOR: Parcelable.Creator<Details> = object : Parcelable.Creator<Details> {
            override fun createFromParcel(`in`: Parcel): Details {
                return Details(`in`)
            }

            override fun newArray(size: Int): Array<Details?> {
                return arrayOfNulls(size)
            }
        }
    }
}
