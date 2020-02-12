package com.feathercompany.www.Dialogs;
import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import org.json.*;
import android.content.SharedPreferences.Editor ;

import com.feathercompany.www.R;

import java.util.*;

public class addTimeAlarm extends DialogFragment
{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setCustomTitle(inflater.inflate(R.layout.addtimelayout,null));
		builder.setView(inflater.inflate(R.layout.settime, null))
			// Add action buttons
			.setPositiveButton(getActivity().getString(R.string.definir_horario), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
			    TimePicker a = getDialog().findViewById(R.id.setime);
				String  hour = a.getCurrentHour().toString();
				String  minutes = a.getCurrentMinute().toString();
				try{
					SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs",getActivity().MODE_PRIVATE);
                    JSONObject obj = new JSONObject();
					obj.put("horas",hour);
					obj.put("minutos",minutes);
					Editor editor = prefs.edit() ;
					editor.putString ("alarme", obj.toString()) ;
					editor.commit();
					System.out.println("Alarme definido para: "+prefs.getString("alarme",null));
					
				}catch(Exception e){
					System.out.println("ERRO WHILE SAVING THE HOUR OF ALARM!!!!");
				}
					System.out.println("CANCELANDO ANTIGO ALARME");
				    Intent intent = new Intent("ALARME_DISPARADO");
					PendingIntent p = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
					AlarmManager alarme = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
					alarme.cancel(p);
					System.out.println("CRIANDO OUTRO ALARME");
					creatingAgain(getActivity());
					
				}
			})
			.setNegativeButton(getActivity().getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				    addTimeAlarm.this.getDialog().cancel();
				}
			});

		return builder.create();
		}
		
		
		public void creatingAgain(Context context){
			
			
				try{
					SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
					System.out.println("CALLING BROADCAST");
					String data = prefs.getString("alarme",null);
					JSONObject obj = new JSONObject(data);
					String hour = obj.getString("horas");
					String minutes = obj.getString("minutos");
					Intent intent = new Intent("ALARME_DISPARADO");
					PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour) );
					calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
					calendar.set(Calendar.SECOND, 00);
					AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
					alarme.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,p);

				}catch(Exception e){
					System.out.println("ERROR WHILE SETTING THE ALARM DATA");
				}
			
			
		}
}
