package com.example.finalproject;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AcademicActivity extends Activity implements OnItemClickListener {

	Button addTakenClass, readFromFile, drop, checkBTN, updatingDB, generateScheduleBtn, sendSchedule;
	ArrayList<AcademicPlans> listDependingonMajor;
	ArrayList<String> classesTaken;
	String[] classesNames;
	ListView listViewTaken;
	boolean[] check;
	ArrayAdapter<String> adapterTaken;
	ProfileDataBase profileDataBase;
	Bundle extras;
	String id;
	TextView id_view, fname_view, lname_view, major_view;
	public static ImageView image_view;
	Profile profile;
	boolean updateDataBase;
	Vibrator vibrate;
	ArrayList<ArrayList<AcademicPlans>> priorityList = new ArrayList<ArrayList<AcademicPlans>>();
	ArrayList<AcademicPlans> classesToTake = new ArrayList<AcademicPlans>();
	ArrayList<ArrayList<Boolean>> conflictsList;
	ArrayList<Boolean> conflicts;
	CharSequence[] advisors = { "Julius Dichter", "Carlos Munoz", "Ausif Mahmood" };
	String[] advisor_Email;
	int totalNumberOfCredits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// creating the academic database
		setContentView(R.layout.academic_layout);
		classesTaken = new ArrayList<String>();
		// getting the id from the profile
		extras = getIntent().getExtras();
		id = extras.getString("id");
		System.out.println("THE ID THAT'S PASSED THROUGH AN INTENT: " + id);

		// Getting that profile from the profile database
		profile = MainActivity.profilesDataBase.getStudentWithID(id);
		System.out.println("THE PROFILE IS NULLLLLLLLLLLLL");

		// initializing elements
		addTakenClass = (Button) findViewById(R.id.pickMajor);
		readFromFile = (Button) findViewById(R.id.button2);
		sendSchedule = (Button) findViewById(R.id.sendButton);
		generateScheduleBtn = (Button) findViewById(R.id.generateButton);
		id_view = (TextView) findViewById(R.id.ID);
		fname_view = (TextView) findViewById(R.id.textView2);
		lname_view = (TextView) findViewById(R.id.textView3);
		major_view = (TextView) findViewById(R.id.textView4);
		image_view = (ImageView) findViewById(R.id.Pic);
		vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		listViewTaken = (ListView) findViewById(R.id.listView1);

		// Modifing the views accoding to the profile
		id_view.setText(id_view.getText() + " " + profile.getId());
		fname_view.setText(fname_view.getText() + " " + profile.getFirstName());
		lname_view.setText(lname_view.getText() + " " + profile.getLastName());
		major_view.setText(major_view.getText() + " " + profile.getMajor());
		image_view.setImageBitmap(MainActivity.profilesDataBase.getPhoto(profile.getBlob()));

		// to modify the list view to classes taken
		if (!profile.getClassTaken().equals("")) {
			String[] list_classesTaken = profile.getClassTaken().split(",");
			for (int j = 0; j < list_classesTaken.length; j++) {
				classesTaken.add(list_classesTaken[j].toString());
			}
		}

		adapterTaken = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1,
				classesTaken);
		listViewTaken.setAdapter(adapterTaken);
		listViewTaken.setOnItemClickListener(this);
		setClickLis(addTakenClass);

		readInfoNeeded();// getting the classes from the database depending on
							// the major

		// to send a schedule via email
		sendSchedule.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder areaOptions = new AlertDialog.Builder(AcademicActivity.this);
				areaOptions.setTitle("Choose An Advisor");
				areaOptions.setSingleChoiceItems(advisors, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							advisor_Email = new String[] { "balhafni@my.bridgeport.edu" };
							break;
						case 1:
							advisor_Email = new String[] { "franden@my.bridgeport.edu" };
							break;
						case 2:
							advisor_Email = new String[] { "jtaskin@my.bridgeport.edu" };
							break;
						}
					}
				});
				areaOptions.setPositiveButton("Select", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						shareViaMail();

					}
				});
				areaOptions.show();
			}
		});

		// to generate a schedule
		generateScheduleBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				maxPriority();
				get5Classes();
			}
		});

	}

	// getting the classes from the database depending on the major
	public void readInfoNeeded() {
		listDependingonMajor = MainActivity.academicDataBase.getClassesWithMajor(profile.getMajor());
		for (int i = 0; i < listDependingonMajor.size(); i++) {
			System.out.println(listDependingonMajor.get(i).toString());
		}
		classesNames = new String[listDependingonMajor.size()];
		for (int i = 0; i < classesNames.length; i++) {
			classesNames[i] = listDependingonMajor.get(i).getClassName();
		}
	}

	public void shareViaMail() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, advisor_Email);
		i.putExtra(Intent.EXTRA_SUBJECT, profile.getFirstName() + profile.getLastName() + "'s Schedule");
		i.putExtra(Intent.EXTRA_TEXT, emailBody());
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	public void setClickLis(Button button) {
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				vibrate.vibrate(200);
				AlertDialog.Builder areaOptions = new AlertDialog.Builder(AcademicActivity.this);
				areaOptions.setTitle("Pick Classes From: " + profile.getMajor());

				check = new boolean[classesNames.length];

				for (int i = 0; i < check.length; i++) {
					check[i] = false;
				}

				areaOptions.setMultiChoiceItems(classesNames, check, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) {
							check[which] = true;
						}

					}
				});

				areaOptions.setPositiveButton("Start", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String classesTaken_String;
						for (int i = 0; i < check.length; i++) {
							if (check[i] == true) {
								if (!classesTaken.contains(classesNames[i]))
									classesTaken.add(classesNames[i].toString());
							}
						}
						// updating the profile database by adding the taken
						// classes
						classesTaken_String = convertArrayToString(classesTaken);
						System.out.println(classesTaken_String);
						Bitmap bitmap = ((Bitmap) image_view.getDrawingCache());
						updateDataBase = MainActivity.profilesDataBase.updateData(profile.getId(),
								profile.getFirstName(), profile.getLastName(), profile.dateOfBirth, profile.getMajor(),
								classesTaken_String, MainActivity.profilesDataBase.getPhoto(profile.getBlob()));
						if (updateDataBase) {
							Toast.makeText(getApplicationContext(), "the database was updated", Toast.LENGTH_LONG)
									.show();
						}
						adapterTaken.notifyDataSetChanged();
					}
				});
				areaOptions.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
					}

				});
				areaOptions.show();
			}
		});
	}

	// to get the max priority of the data
	public int maxPriority() {
		int maxPri = 0;
		int pri;
		for (int i = 0; i < listDependingonMajor.size(); i++) {
			pri = listDependingonMajor.get(i).getPriority();
			if (maxPri < pri) {
				maxPri = pri;
			}
		}
		createPriorityList(maxPri);
		return maxPri;
	}

	public void createPriorityList(int num) {
		boolean delete = false;
		priorityList = new ArrayList<ArrayList<AcademicPlans>>();
		for (int i = 0; i <= num; i++) {
			// ArrayList<AcademicPlans> acPlans =
			priorityList.add(new ArrayList<AcademicPlans>());
		}
		for (int j = 0; j < listDependingonMajor.size(); j++) {
			AcademicPlans plan = listDependingonMajor.get(j);

			for (int k = 0; k < classesTaken.size(); k++) {
				if (plan.getClassName().equals(classesTaken.get(k))) {
					delete = true;
					break;
				}
			}
			if (delete == false) {
				priorityList.get(listDependingonMajor.get(j).getPriority()).add(plan);
			} else
				delete = false;
		}
	}

	public int findPriorityListToChooseFrom() {
		int minPrioritylist = -1;
		boolean notChecked = false;
		for (int i = conflictsList.size() - 1; i >= 0; i--) {
			notChecked = false;
			for (int j = 0; j < conflictsList.get(i).size(); j++) {

				if (conflictsList.get(i).isEmpty() == false && conflictsList.get(i).get(j) == false) {
					notChecked = true;
					break;
				}
			}
			if (notChecked == true) {
				minPrioritylist = i;
			}
		}
		return minPrioritylist;
	}

	public ArrayList<AcademicPlans> get5Classes() {
		classesToTake = new ArrayList<AcademicPlans>();
		conflictsList = new ArrayList<ArrayList<Boolean>>();
		for (int i = 0; i < priorityList.size(); i++) {
			conflicts = new ArrayList<Boolean>();
			if (priorityList.get(i).isEmpty() == false) {
				for (int j = 0; j < priorityList.get(i).size(); j++) {
					conflicts.add(false);
				}
			}
			conflictsList.add(conflicts);

		}

		while (classesToTake.size() != 5) {
			int minPriority = findPriorityListToChooseFrom();
			if (minPriority == -1) {
				break;
			} else {
				Random rand = new Random();
				int randNum = rand.nextInt(priorityList.get(minPriority).size());
				while (conflictsList.get(minPriority).get(randNum) == true) {
					randNum = rand.nextInt(priorityList.get(minPriority).size());
				}
				if (checkConflicts(classesToTake, priorityList.get(minPriority).get(randNum))) {
					conflictsList.get(minPriority).set(randNum, true);
				} else {
					classesToTake.add(priorityList.get(minPriority).get(randNum));
					priorityList.get(minPriority).remove(randNum);
					conflictsList.get(minPriority).remove(randNum);
				}
			}
		}

		StringBuilder str = new StringBuilder();
		for (int h = 0; h < classesToTake.size(); h++) {
			str.append(classesToTake.get(h).getClassName()).append("\n");
			totalNumberOfCredits += classesToTake.get(h).getCredits();
		}
		str.append("\n").append("Total number of credits: " + totalNumberOfCredits);
		showMessage("Here Are Your Classes", str.toString());
		return classesToTake;
	}

	public boolean checkNamesPriority(AcademicPlans class1, AcademicPlans class2) {
		boolean dontTakeBoth = false;

		int priority1, priority2;
		priority1 = class1.getPriority();
		priority2 = class2.getPriority();

		String[] namearr1, namearr2;
		namearr1 = class1.getClassName().split("-");
		namearr2 = class2.getClassName().split("-");

		if (namearr1[0].equals(namearr2[0]) && priority1 != priority2) {
			dontTakeBoth = true;
		}

		return dontTakeBoth;
	}

	public boolean checkConflicts(ArrayList<AcademicPlans> classesToTake, AcademicPlans class1) {
		boolean check = false;
		if (classesToTake.isEmpty()) {
			check = false;
		} else {
			boolean sameDay = false;
			for (int i = 0; i < classesToTake.size(); i++) {
				sameDay = false;
				if (checkDays(classesToTake.get(i).getClassTime(), class1.getClassTime())
						|| checkNamesPriority(classesToTake.get(i), class1)) {
					sameDay = true;
					break;
				}
			}
			if (sameDay == true) {
				check = true;
			}
		}

		return check;
	}

	public boolean checkDays(String a, String b) {
		int j = 0;
		boolean same = false;
		while (a.charAt(j) != ',') {
			if (b.contains(a.substring(j, j + 2))) {
				String[] array1 = a.split(",");
				String[] array2 = b.split(",");
				if (checkTimes(array1[1], array2[1])) {
					same = true;
					break;
				}

			}
			j += 2;
		}
		return same;

	}

	public boolean checkTimes(String a, String b) {
		boolean conflicts = false;

		String[] arrA = a.split("-");
		String[] arrB = b.split("-");

		String[] arrAstart = arrA[0].split(":");
		String[] arrAend = arrA[1].split(":");
		String[] arrBstart = arrB[0].split(":");
		String[] arrBend = arrB[1].split(":");

		int startA, endA, startB, endB;
		startA = Integer.parseInt(arrAstart[0]);
		endA = Integer.parseInt(arrAend[0]);
		startB = Integer.parseInt(arrBstart[0]);
		endB = Integer.parseInt(arrBend[0]);

		int startAmin, endAmin, startBmin, endBmin;
		startAmin = Integer.parseInt(arrAstart[1]);
		endAmin = Integer.parseInt(arrAend[1]);
		startBmin = Integer.parseInt(arrBstart[1]);
		endBmin = Integer.parseInt(arrBend[1]);

		if (startA < 8) {
			startA += 12;
		}
		if (startB < 8) {
			startB += 12;
		}
		if (endA < 9) {
			endA += 12;
		}
		if (endB < 9) {
			endB += 12;
		}

		if (startA >= startB && startA <= endB) {// checks if class A starts
													// while class B is going on
			if (startA == startB) {
				if (startAmin >= startBmin) {
					conflicts = true;
					// return true;
				}
			} else if (startA == endB) {
				if (startAmin <= endBmin) {
					conflicts = true;
					// return true;
				}
			}
		} else if (startB >= startA && startB <= endA) {// Checks if class B
														// starts while class A
														// is going on
			if (startA == startB) {
				if (startAmin <= startBmin) {
					conflicts = true;
					// return true;
				}
			} else if (startB == endA) {
				if (startBmin <= endAmin) {
					conflicts = true;
					// return true;
				}
			}
		} else if (endA >= startB && endA <= endB) {// checks if class A ends
													// while class B is going on
			if (endA == startB) {
				if (endAmin >= startBmin) {
					conflicts = true;
					// return true;
				}
			} else if (endA == endB) {
				if (endAmin <= endBmin) {
					conflicts = true;
					// return true;
				}
			}
		} else if (endB >= startA && endB <= endA) {// checks if class B ends
													// while class A is going on
			if (endB == startA) {
				if (endBmin >= startAmin) {
					conflicts = true;
					// return true;
				}
			} else if (endB == endA) {
				if (endBmin <= endAmin) {
					conflicts = true;
					// return true;
				}
			}
		}

		return conflicts;
	}

	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}

	public String convertArrayToString(ArrayList<String> array) {
		String str = "";
		for (int i = 0; i < array.size(); i++) {
			str = str + array.get(i);
			// Do not append comma at the end of last element
			if (i < array.size() - 1) {
				str = str + ",";
			}
		}
		return str;
	}

	public String emailBody() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < classesToTake.size(); i++) {
			str.append(classesToTake.get(i).getClassName()).append(" " + classesToTake.get(i).getClassTime())
					.append("\n");
		}
		str.append("\n").append("Total number of credits " + totalNumberOfCredits);
		return str.toString();
	}

	public ArrayList<String> convertStringToArray(String str) {
		String[] arr = str.split(",");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}
}
