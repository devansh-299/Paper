package com.devansh.paper.view.fragment

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devansh.core.data.Note
import com.devansh.paper.ImageHelper
import com.devansh.paper.R
import com.devansh.paper.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_note_details.*


class NoteDetailsFragment : BottomSheetDialogFragment() {

    private val PERMISSION_CODE = 299
    private val REQUEST_CODE = 29
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note(0L, "", "", 0L, 0L)

    companion object {
        private const val NOTE_ID = "note_id"

        fun newInstance(noteId: Long) = NoteDetailsFragment().apply {
            arguments = bundleOf(
                NOTE_ID to noteId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            val noteId = it.getLong(NOTE_ID)
            // if the note exists
            if (noteId != 0L) {
                bottomsheet_header.text = "Update Note"
                viewModel.fetchNoteDetails(noteId)
            }
        }

        bottomsheet_delete_note.setOnClickListener { deleteNote() }

        iv_note.setOnClickListener { checkPermission() }

        chip_save.setOnClickListener {
            if (bottomsheet_title.text.toString() != "" ||
                bottomsheet_content.text.toString() != "") {
                val currentTime = System.currentTimeMillis()
                currentNote.title = bottomsheet_title.text.toString()
                currentNote.content = bottomsheet_content.text.toString()
                currentNote.updateTime = currentTime
                // checking if the note already exists, 0 is the default value!
                if (currentNote.id == 0L) {
                    currentNote.creationTime = currentTime
                }
                // for saving the note
                viewModel.saveNote(currentNote)
                dismiss()
            } else {
                Toast.makeText(context,getString(R.string.enter_all_details), Toast.LENGTH_SHORT )
                    .show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.noteSaved.observe(this, Observer {
            // if noteSaved == true
            if (it) {
                hideKeyboard()
                Toast.makeText(context, getString(R.string.note_saved), Toast.LENGTH_SHORT ).show()
            } else {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT ).show()
            }
        })

        viewModel.currentNote.observe(this, Observer { note ->
            note?.let {
                currentNote = it
                bottomsheet_title.setText(it.title, TextView.BufferType.EDITABLE)
                bottomsheet_content.setText(it.content, TextView.BufferType.EDITABLE)
                it.image?.let { it1 -> ImageHelper.showImage(context!!, it1, iv_note) }
            }
        })

        viewModel.deletedNote.observe(this, Observer {
            if (it) {
                Toast.makeText(context, getString(R.string.deleted), Toast.LENGTH_SHORT ).show()
            }
        })
    }

    private fun deleteNote() = if (currentNote.id != 0L) {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.delete_note_title))
            .setMessage(getString(R.string.delete_note_message))
            .setPositiveButton(getString(R.string.yes)) {
                    dialog, i -> viewModel.deleteNote(currentNote)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, i -> dialog.dismiss() }
            .create()
            .show()
    } else {
        Toast.makeText(context, getString(R.string.cannot_delete), Toast.LENGTH_SHORT ).show()
    }

    private fun checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromStorage();
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromStorage();
        }
    }

    private fun pickImageFromStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromStorage()
                }
                else{
                    //permission from popup denied
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val uri: Uri? = data?.data
            val imageByteArray = ImageHelper.covertUriToByteArray(context!!, uri!!)
            ImageHelper.showImage(context!!, imageByteArray, iv_note)
            currentNote.image = imageByteArray
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE)
                 as InputMethodManager
        // any view can be passed
        inputMethodManager.hideSoftInputFromWindow(bottomsheet_title.windowToken, 0)
    }
}
