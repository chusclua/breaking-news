package com.chus.clua.breakingnews.presentation.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.chus.clua.breakingnews.domain.model.NamedEntity

class EntityListDialogFragment<E>(
    private val title: String,
    private val entityList: List<NamedEntity>,
    private val entityClickListener: (entity: E) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setSingleChoiceItems(entityList.map { it.name }.toTypedArray(), -1) { _, which ->
                    entityClickListener(entityList[which] as E)
                    dismiss()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "EntityListDialogFragment"
    }
}