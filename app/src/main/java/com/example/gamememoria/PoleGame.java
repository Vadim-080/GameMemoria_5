package com.example.gamememoria;

import static com.example.gamememoria.B_Menu.Shirin_fishek;
import static com.example.gamememoria.B_Menu.Visot_fishek;
import static com.example.gamememoria.B_Menu.uroven;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class PoleGame extends BaseAdapter {

    private Context mContext;
    private Integer mCols, mRows;

    byte rubashka, naborKartinKart;
    private ArrayList<String> arrPict; // массив картинок
    private String PictureCollection; // Префикс набора картинок
    private Resources mRes; // Ресурсы приложени

    private static enum Status {CELL_OPEN, CELL_CLOSE, CELL_DELETE}

    private ArrayList<Status> arrStatus; // состояние ячеек

    public PoleGame(Context context, int cols, int rows) {
        mContext = context;
        mCols = cols;
        mRows = rows;

        rubashka = (byte) (Math.random() * 28); // Случайное число от 0 до 27 -- Для выбора рубашки карт
        naborKartinKart = (byte) (Math.random() * 4); // Случайное число от 0 до 3 -- Для выбора картинок карт

        arrPict = new ArrayList<String>();
        arrStatus = new ArrayList<Status>();
        // Пока определяем префикс так, позже он будет браться из настроек

        switch (naborKartinKart) {
            case 0:
                PictureCollection = "animal";
                break;
            case 1:
                PictureCollection = "animal_a";
                break;
            case 2:
                PictureCollection = "animal_b";
                break;
            case 3:
                PictureCollection = "animal_c";
                break;
        }

        // Получаем все ресурсы приложения
        mRes = mContext.getResources();

        // Метод заполняющий массив vecPict
        makePictArray();
        // Метод устанавливающий всем ячейкам статус CELL_CLOSE
        closeAllCells();
    }

    private void closeAllCells() {
        arrStatus.clear();
        for (int i = 0; i < mCols * mRows; i++)
            arrStatus.add(Status.CELL_CLOSE);
    }

    private void makePictArray() {
        // очищаем вектор
        arrPict.clear();
        // добавляем
        for (int i = 0; i < ((mCols * mRows) / 2); i++) {
            arrPict.add(PictureCollection + Integer.toString(i));
            arrPict.add(PictureCollection + Integer.toString(i));
        }
        // перемешиваем
        Collections.shuffle(arrPict);
    }

    @Override
    public int getCount() {
        return mCols * mRows;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView view; // для вывода картинки

        if (convertView == null) {
            view = new ImageView(mContext);
            view.setLayoutParams(new GridView.LayoutParams(Shirin_fishek, Visot_fishek));  // Задаём размеры элемента
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setPadding(8, 8, 8, 8);
        } else
            view = (ImageView) convertView;

        switch (arrStatus.get(position)) {
            case CELL_OPEN:
                // Получаем идентификатор ресурса для картинки,
                // которая находится в векторе vecPict на позиции position
                Integer drawableId = mRes.getIdentifier(arrPict.get(position),
                        "drawable", mContext.getPackageName());
                view.setImageResource(drawableId);
                break;
            case CELL_CLOSE:

                switch (rubashka) {
                    case 0:
                        view.setImageResource(R.drawable.close1);
                        break;
                    case 1:
                        view.setImageResource(R.drawable.close2);
                        break;
                    case 2:
                        view.setImageResource(R.drawable.close3);
                        break;
                    case 3:
                        view.setImageResource(R.drawable.close4);
                        break;
                    case 4:
                        view.setImageResource(R.drawable.close5);
                        break;
                    case 5:
                        view.setImageResource(R.drawable.close6);
                        break;
                    case 6:
                        view.setImageResource(R.drawable.close7);
                        break;
                    case 7:
                        view.setImageResource(R.drawable.close8);
                        break;
                    case 8:
                        view.setImageResource(R.drawable.close9);
                        break;
                    case 9:
                        view.setImageResource(R.drawable.close10);
                        break;
                    case 10:
                        view.setImageResource(R.drawable.close11);
                        break;
                    case 11:
                        view.setImageResource(R.drawable.close12);
                        break;
                    case 12:
                        view.setImageResource(R.drawable.close13);
                        break;
                    case 13:
                        view.setImageResource(R.drawable.close14);
                        break;
                    case 14:
                        view.setImageResource(R.drawable.close15);
                        break;
                    case 15:
                        view.setImageResource(R.drawable.close16);
                        break;
                    case 16:
                        view.setImageResource(R.drawable.close17);
                        break;
                    case 17:
                        view.setImageResource(R.drawable.close18);
                        break;
                    case 18:
                        view.setImageResource(R.drawable.close19);
                        break;
                    case 19:
                        view.setImageResource(R.drawable.close20);
                        break;
                    case 20:
                        view.setImageResource(R.drawable.close21);
                        break;
                    case 21:
                        view.setImageResource(R.drawable.close22);
                        break;
                    case 22:
                        view.setImageResource(R.drawable.close23);
                        break;
                    case 23:
                        view.setImageResource(R.drawable.close24);
                        break;
                    case 24:
                        view.setImageResource(R.drawable.close25);
                        break;
                    case 25:
                        view.setImageResource(R.drawable.close26);
                        break;
                    case 26:
                        view.setImageResource(R.drawable.close27);
                        break;
                    case 27:
                        view.setImageResource(R.drawable.close28);
                        break;

                }
                break;
            default:
                if (uroven > 7 | uroven < 11 ) {
                    byte zastOtkrKart = (byte) (Math.random() * 4); // Случайное число от 0 до 3
                    switch (zastOtkrKart) {
                        case 0:
                            view.setImageResource(R.drawable.none1);
                            break;
                        case 1:
                            view.setImageResource(R.drawable.none);
                            break;
                        case 2:
                            view.setImageResource(R.drawable.none);
                            break;
                        case 3:
                            view.setImageResource(R.drawable.none);
                            break;
                    }
                } else {
                    view.setImageResource(R.drawable.none);
                }

                if (uroven > 10 | uroven < 14 ) {
                    byte zastOtkrKart = (byte) (Math.random() * 2); // Случайное число от 0 до 1
                    switch (zastOtkrKart) {
                        case 0:
                            view.setImageResource(R.drawable.none1);
                            break;
                        case 1:
                            view.setImageResource(R.drawable.none);
                            break;
                    }
                } else {
                    view.setImageResource(R.drawable.none);
                }

                if (uroven > 13 ) {
                    byte zastOtkrKart = (byte) (Math.random() * 4); // Случайное число от 0 до 32
                    switch (zastOtkrKart) {
                        case 0:
                            view.setImageResource(R.drawable.none);
                            break;
                        case 1:
                            view.setImageResource(R.drawable.none1);
                            break;
                        case 2:
                            view.setImageResource(R.drawable.none1);
                            break;
                        case 3:
                            view.setImageResource(R.drawable.none1);
                            break;
                    }
                } else {
                    view.setImageResource(R.drawable.none);
                }
        }
        return view;
    }

    public void checkOpenCells() {
        int first = arrStatus.indexOf(Status.CELL_OPEN);
        int second = arrStatus.lastIndexOf(Status.CELL_OPEN);
        if (first == second)
            return;
        if (arrPict.get(first).equals(arrPict.get(second))) {
            arrStatus.set(first, Status.CELL_DELETE);
            arrStatus.set(second, Status.CELL_DELETE);
        } else {
            arrStatus.set(first, Status.CELL_CLOSE);
            arrStatus.set(second, Status.CELL_CLOSE);
        }
        return;
    }

    public boolean openCell(int position) {
        if (arrStatus.get(position) == Status.CELL_DELETE
                || arrStatus.get(position) == Status.CELL_OPEN)
            return false;

        if (arrStatus.get(position) != Status.CELL_DELETE)
            arrStatus.set(position, Status.CELL_OPEN);

        notifyDataSetChanged();
        return true;
    }

    public boolean checkGameOver() {
        if (arrStatus.indexOf(Status.CELL_CLOSE) < 0)
            return true;
        return false;
    }

}