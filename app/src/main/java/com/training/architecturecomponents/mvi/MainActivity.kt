package com.training.architecturecomponents.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.training.architecturecomponents.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var resultTv:TextView
    lateinit var sendBtn :Button

    private val viewModel : AddNumberViewModel by lazy{
        ViewModelProvider(this).get(AddNumberViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTv = findViewById(R.id.result_tv)
        sendBtn = findViewById(R.id.send_btn)

        // render
        render()

        // send
        sendBtn.setOnClickListener{
            // send
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(AddNumberIntent.AddNumber)
            }
        }



    }

    private fun render(){

        lifecycleScope.launch {
            viewModel.states.collectLatest {
                when(it){
                    is AddNumberViewStates.AddResult ->{
                        Log.e("number in main", "render: ${it.number}")
                        resultTv.text = it.number.toString()
                    }
                    is AddNumberViewStates.Error -> {
                        Log.e("number in main", "render: ${it.mess}")
                        resultTv.text = it.mess
                    }
                    AddNumberViewStates.Idle -> {
                        Log.e("number in main", "IDLE")
                        resultTv.text = "Idle"
                    }
                }
            }
        }
    }
}




/* MVI :
    look to user as a fuction
    immutable inputs
    known view states
    safe to use
 */

/*
Activity >> render & send
            >> ViewStates & Intents
ViewModel >> process & reduce
 */

/*
    Render(Reduce(process(send())))
 */

