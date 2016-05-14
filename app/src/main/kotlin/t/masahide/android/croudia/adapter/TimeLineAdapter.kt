package t.masahide.android.croudia.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.annotation.ColorRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.squareup.picasso.Picasso
import t.masahide.android.croudia.R
import t.masahide.android.croudia.databinding.TimeLineRowBinding
import t.masahide.android.croudia.databinding.TimeLineRowShareBinding
import t.masahide.android.croudia.entitiy.Status
import t.masahide.android.croudia.entitiy.User
import t.masahide.android.croudia.service.PreferenceService

/**
 * Created by Masahide on 2016/05/05.
 */

class TimeLineAdapter(context: Context, resource: Int, statusList: MutableList<Status>) : ArrayAdapter<Status>(context, resource, statusList) {

    lateinit var user: User

    init {
        val preferenceService = PreferenceService()
        user = preferenceService.getUser()
    }


    enum class ViewType() {
        NORMAL,
        SHARE
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position).spreadStatus?.let {
            return ViewType.SHARE.ordinal
        }
        return ViewType.NORMAL.ordinal
    }

    override fun getViewTypeCount(): Int {
        return ViewType.values().size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (getItemViewType(position) == ViewType.NORMAL.ordinal) {
            return getNormalView(convertView, parent, position)
        } else {
            return getShareView(convertView, parent, position)
        }
    }

    private fun getNormalView(convertView: View?, parent: ViewGroup?, position: Int): View {
        val view: View
        val dataBinding: TimeLineRowBinding

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            dataBinding = DataBindingUtil.inflate(inflater, R.layout.time_line_row, parent, false)
            view = dataBinding.root
            view.tag = dataBinding
        } else {
            dataBinding = convertView.tag as TimeLineRowBinding
            view = convertView
        }
        val status = getItem(position)
        Picasso.with(context).load(status.user.profileImageUrl).into(dataBinding.userIcon)
        Picasso.with(context).load(status.entities?.media?.mediaUrl).into(dataBinding.imgMedia)
        dataBinding.txtShare.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        dataBinding.favorite.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        dataBinding.imgReply.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        dataBinding.imgDelete.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        dataBinding.status = status
        dataBinding.imgDelete.visibility = if (status.user.id == user.id) View.VISIBLE else View.INVISIBLE
        dataBinding.parentLayout.setBackgroundResource(if (status.inReplyToUserId == user.id) R.color.reply_bg else R.color.translate)
        return view
    }

    private fun getShareView(convertView: View?, parent: ViewGroup?, position: Int): View {
        val shareDataBinding: TimeLineRowShareBinding
        val view: View
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            shareDataBinding = DataBindingUtil.inflate(inflater, R.layout.time_line_row_share, parent, false)
            view = shareDataBinding.root
            view.tag = shareDataBinding
        } else {
            shareDataBinding = convertView.tag as TimeLineRowShareBinding
            view = convertView
        }
        val status = getItem(position)
        status.spreadStatus?.let {
            Picasso.with(context).load(it.user.profileImageUrl).into(shareDataBinding.userIcon)
            Picasso.with(context).load(it.entities?.media?.mediaUrl).into(shareDataBinding.imgMedia)
        }
        shareDataBinding.txtShare.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        shareDataBinding.favorite.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }
        shareDataBinding.imgReply.setOnClickListener {
            (parent as ListView).performItemClick(parent, position, it.id.toLong())
        }

        shareDataBinding.imgDelete.visibility = View.INVISIBLE
        shareDataBinding.status = status
        return view
    }
}