package com.example.reminderapp.ui.home

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.reminderapp.PagerRecyclerAdapter
import com.example.reminderapp.R
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    var todayDate: Calendar?=null
    val returnButton: Button?=null
    var todayPos:Int=0;

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        val data=ArrayList<ArrayList<String>>()
        val subdata=ArrayList<String>()
        for(j in 1..10){
            subdata.add("$j 번째")
        }
        for(i in 1..5) {
            data.add(subdata)
        }
        val returnLayout=view.findViewById<ConstraintLayout>(R.id.returnlayout)

        val pager=view.findViewById<ViewPager2>(R.id.dailydatapager)
        val mAdapter=PagerRecyclerAdapter(requireContext(), data)
        pager.adapter=mAdapter

        val calendarExpandButton= view.findViewById<ImageButton>(R.id.calendarExpandButton)
        val calender=view.findViewById<MaterialCalendarView>(R.id.materialCalendar)
        val dateLayout =view.findViewById<ConstraintLayout>(R.id.datelayout)
        var dateText=view.findViewById<TextView>(R.id.dateTextView)
        var todayText=view.findViewById<TextView>(R.id.todayTextView)

        todayDate=Calendar.getInstance()
        pager.setCurrentItem(todayToInt())

        var datedata: SimpleDateFormat = SimpleDateFormat("EEEE, YYYY-MM-dd")
        calender.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).setFirstDayOfWeek(Calendar.SUNDAY).commit()
        calender.setCurrentDate(todayDate)
        calender.setSelectedDate(todayDate)
        calender.topbarVisible=false

        calender.setOnDateChangedListener { widget, date, selected ->
            pager.setCurrentItem(dateToInt(date.calendar),false) //setPage
            dateText.setText(datedata.format(date.calendar!!.time))
            todayText.setText(dToDate(pager.currentItem))
        }
        pager.registerOnPageChangeCallback( object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val tmpCal=Calendar.getInstance()
                tmpCal.set(1997,3,1)
                tmpCal.add(Calendar.DATE, position+1)
                calender.setSelectedDate(tmpCal)
                calender.setCurrentDate(tmpCal)

                dateText.setText(datedata.format(tmpCal!!.time))
                todayText.setText(dToDate(position))
                super.onPageSelected(position)
            }
        }
        )

        calendarExpandButton.setOnClickListener { v: View? ->
            if(dateLayout.visibility==View.GONE)
            {
                val autoTransition = AutoTransition()
                autoTransition.duration = 100
                TransitionManager.beginDelayedTransition(container,autoTransition)
                calender.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).setFirstDayOfWeek(Calendar.SUNDAY).commit()
                dateLayout.visibility=View.VISIBLE
                calender.topbarVisible=false
            }
            else
            {
                val autoTransition = AutoTransition()
                autoTransition.duration = 100
                TransitionManager.beginDelayedTransition(container,autoTransition)
                calender.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).setFirstDayOfWeek(Calendar.SUNDAY).commit()
                dateLayout.visibility=View.GONE
                calender.topbarVisible=true
            }
        }
        returnLayout.setOnClickListener { v: View? ->
            todayDate= Calendar.getInstance()
            pager.setCurrentItem(todayToInt())
        }
        return view
    }

    fun todayToInt() :Int
    {
        todayDate=Calendar.getInstance()
        val initdate=Calendar.getInstance()
        initdate.set(1997,3,1)
        val todaydate=todayDate
        val diffTime:Long=todaydate!!.timeInMillis-initdate.timeInMillis
        val convert= TimeUnit.MILLISECONDS.toDays(diffTime);
        var i:Int=convert.toInt()
        Log.d("testd","fuun: "+i)
        initdate.add(Calendar.DATE,i)
        return i-1
    }

    fun dateToInt(date:Calendar) :Int
    {
        val initdate=Calendar.getInstance()
        initdate.set(1997,3,1)
        val todaydate=date
        val diffTime:Long=todaydate.timeInMillis-initdate.timeInMillis
        val convert= TimeUnit.MILLISECONDS.toDays(diffTime);
        var i:Int=convert.toInt()
        initdate.add(Calendar.DATE,i)
        return i
    }

    fun dToDate(selectedDate:Int): String
    {
        todayDate= Calendar.getInstance()
        val today= todayToInt()
        val dDay=selectedDate- today!!
        if(dDay==0)
        {
            return "Today"
        }
        else if(dDay>0)
        {
            val tmp:String="D+"+"$dDay"
            return  tmp
        }
        else
        {
            val tmp:String="D$dDay"
            return tmp
        }
    }
}