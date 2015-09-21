package com.example.sergbek.converterlab.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sergbek.converterlab.model.Currencies;
import com.example.sergbek.converterlab.model.RootModelOrganization;
import com.example.sergbek.converterlab.model.CurrencyModel;
import com.example.sergbek.converterlab.model.Organization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DatabaseController implements AbstractControllerData {

    private DatabaseHelper mDatabaseHelper;

    private static final String QUERY_JOIN_ORGANIZATION = " SELECT s1._id, " +
            " s1.title," +
            " s1.address," +
            " s1.phone," +
            " s1.link," +
            " s2.val_regions," +
            " s3.val_cities," +
            " s4.val_orgtype" +
            " FROM organization s1" +
            " INNER JOIN" +
            " regions s2 ON s1.regionId = s2._id" +
            " INNER JOIN" +
            " cities s3 ON s1.cityId = s3._id" +
            " INNER JOIN" +
            " orgtypes s4 ON s1.orgType = s4._id";


    private static final String QUERY_WHERE_ORGANIZATION = " WHERE s1._id =";

    public DatabaseController(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void addOrganization(List<Organization> listOrganizations) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_ORGANIZATION, null, null);

        for (int i = 0; i < listOrganizations.size(); i++) {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, listOrganizations.get(i).getId());
            values.put(DatabaseHelper.COLUMN_TITLE, listOrganizations.get(i).getTitle());
            values.put(DatabaseHelper.COLUMN_ADDRESS, listOrganizations.get(i).getAddress());
            values.put(DatabaseHelper.COLUMN_PHONE, listOrganizations.get(i).getPhone());
            values.put(DatabaseHelper.COLUMN_LINK, listOrganizations.get(i).getLink());
            values.put(DatabaseHelper.COLUMN_OLD_ID, listOrganizations.get(i).getOldId());
            values.put(DatabaseHelper.COLUMN_CITY_ID, listOrganizations.get(i).getCityId());
            values.put(DatabaseHelper.COLUMN_REGION_ID, listOrganizations.get(i).getRegionId());
            values.put(DatabaseHelper.COLUMN_ORGTYPE, listOrganizations.get(i).getOrgType());

            db.insert(DatabaseHelper.TABLE_ORGANIZATION, null, values);
        }

        db.close();
    }

    @Override
    public void addRegions(Map<String, String> mapRegions) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_REGIONS, null, null);

        for (Map.Entry<String, String> entry : mapRegions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, key);
            values.put(DatabaseHelper.COLUMN_VALUES_REGIONS, value);

            db.insert(DatabaseHelper.TABLE_REGIONS, null, values);
        }

        db.close();
    }

    @Override
    public void addCities(Map<String, String> mapCities) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CITIES, null, null);

        for (Map.Entry<String, String> entry : mapCities.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, key);
            values.put(DatabaseHelper.COLUMN_VALUES_CITIES, value);

            db.insert(DatabaseHelper.TABLE_CITIES, null, values);
        }

        db.close();
    }

    @Override
    public void addCurrencies(Map<String, String> mapCurrencies) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CURRENCIES, null, null);

        for (Map.Entry<String, String> entry : mapCurrencies.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, key);
            values.put(DatabaseHelper.COLUMN_VALUES_CURRENCIES, value);

            db.insert(DatabaseHelper.TABLE_CURRENCIES, null, values);
        }

        db.close();
    }

    @Override
    public void addOrgTypes(Map<String, String> mapOrgTypes) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_ORGTYPES, null, null);

        for (Map.Entry<String, String> entry : mapOrgTypes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, key);
            values.put(DatabaseHelper.COLUMN_VALUES_ORGTYPE, value);

            db.insert(DatabaseHelper.TABLE_ORGTYPES, null, values);
        }

        db.close();
    }

    @Override
    public void addCourse(List<Organization> listOrganizations) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        for (Organization organization : listOrganizations) {
            for (CurrencyModel currencyModel : organization.getCurrencies().getCurrencyModels()) {
                Cursor cursor = db.query(DatabaseHelper.TABLE_COURSE, new String[]{DatabaseHelper.COLUMN_ASK, DatabaseHelper.COLUMN_BID},
                        DatabaseHelper.COLUMN_ABBR + "=? AND "
                                + DatabaseHelper.COLUMN_ORGANIZATION + "=?", new String[]
                                {currencyModel.getAbbr(), organization.getId()}, null, null, null);
                try {
                    cursor.moveToNext();
                    BigDecimal ask = new BigDecimal(cursor.getString(0));
                    BigDecimal bid = new BigDecimal(cursor.getString(1));
                    BigDecimal askNew = new BigDecimal(currencyModel.getAsk());
                    BigDecimal bidNew = new BigDecimal(currencyModel.getBid());

                    if (askNew.compareTo(ask) >= 0)
                        currencyModel.setChangeAsk(1);
                    else
                        currencyModel.setChangeAsk(0);

                    if (bidNew.compareTo(bid) >= 0)
                        currencyModel.setChangeBit(1);
                    else
                        currencyModel.setChangeBit(0);

                } catch (Exception e) {
                    //Если БД создается первый раз, то ставим флаг 1
                    currencyModel.setChangeAsk(1);
                    currencyModel.setChangeBit(1);
                } finally {
                    cursor.close();
                }
            }
        }
        db.delete(DatabaseHelper.TABLE_COURSE, null, null);

        putCourseInDataBase(listOrganizations, db);

        db.close();
    }

    private void putCourseInDataBase(List<Organization> listOrganizations, SQLiteDatabase db) {
        for (Organization organization : listOrganizations) {
            for (CurrencyModel currencyModel : organization.getCurrencies().getCurrencyModels()) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_CHANGE_ASK, currencyModel.getChangeAsk());
                values.put(DatabaseHelper.COLUMN_CHANGE_BID, currencyModel.getChangeBit());
                values.put(DatabaseHelper.COLUMN_ABBR, currencyModel.getAbbr());
                values.put(DatabaseHelper.COLUMN_FULL_TITLE, currencyModel.getFullTitle());
                values.put(DatabaseHelper.COLUMN_ORGANIZATION, organization.getId());
                values.put(DatabaseHelper.COLUMN_ASK, currencyModel.getAsk());
                values.put(DatabaseHelper.COLUMN_BID, currencyModel.getBid());

                db.insert(DatabaseHelper.TABLE_COURSE, null, values);
            }
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> organizationList = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery(QUERY_JOIN_ORGANIZATION, null);

        if (cursor.moveToFirst()) {
            do {
                Organization organization = new Organization();

                organization.setId(cursor.getString(0));
                organization.setTitle(cursor.getString(1));
                organization.setAddress(cursor.getString(2));
                organization.setPhone(cursor.getString(3));
                organization.setLink(cursor.getString(4));
                organization.setRegionId(cursor.getString(5));
                organization.setCityId(cursor.getString(6));
                organization.setOrgType(cursor.getString(7));

                Currencies currencies = new Currencies();
                currencies.setCurrencyModels(getListCurrencyModel(db, cursor.getString(0)));

                organization.setCurrencies(currencies);

                organizationList.add(organization);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return organizationList;
    }

    @Override
    public Organization getOrganization(String id) {
        Organization organization = new Organization();
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(QUERY_JOIN_ORGANIZATION + QUERY_WHERE_ORGANIZATION
                + "'" + id + "'", null);

        if (cursor != null)
            cursor.moveToFirst();

        organization.setId(cursor.getString(0));
        organization.setTitle(cursor.getString(1));
        organization.setAddress(cursor.getString(2));
        organization.setPhone(cursor.getString(3));
        organization.setLink(cursor.getString(4));
        organization.setRegionId(cursor.getString(5));
        organization.setCityId(cursor.getString(6));
        organization.setOrgType(cursor.getString(7));

        Currencies currencies = new Currencies();
        currencies.setCurrencyModels(getListCurrencyModel(db, cursor.getString(0)));

        organization.setCurrencies(currencies);

        cursor.close();
        db.close();

        return organization;
    }


    private List<CurrencyModel> getListCurrencyModel(SQLiteDatabase db, String key) {
        List<CurrencyModel> currencyModelList = new ArrayList<>();

        Cursor cursor = db.query(DatabaseHelper.TABLE_COURSE, new String[]{DatabaseHelper.COLUMN_ABBR,
                        DatabaseHelper.COLUMN_FULL_TITLE, DatabaseHelper.COLUMN_ASK,
                        DatabaseHelper.COLUMN_BID, DatabaseHelper.COLUMN_CHANGE_ASK,
                        DatabaseHelper.COLUMN_CHANGE_BID}, DatabaseHelper.COLUMN_ORGANIZATION + "=?",
                new String[]{String.valueOf(key)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CurrencyModel currencyModel = new CurrencyModel();
                currencyModel.setAbbr(cursor.getString(0));
                currencyModel.setFullTitle(cursor.getString(1));
                currencyModel.setAsk(cursor.getString(2));
                currencyModel.setBid(cursor.getString(3));
                currencyModel.setChangeAsk(cursor.getInt(4));
                currencyModel.setChangeBit(cursor.getInt(5));

                currencyModelList.add(currencyModel);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return currencyModelList;
    }

    @Override
    public void setDateBase(RootModelOrganization rootModelOrganization) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_DATE, null, null);

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATE, rootModelOrganization.getDate());

        db.insert(DatabaseHelper.TABLE_DATE, null, values);

        db.close();
    }

    @Override
    public String getDate() {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_DATE, new String[]{DatabaseHelper.COLUMN_DATE}, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            String data = cursor.getString(0);

            return data;
        }
        cursor.close();
        db.close();

        return "in dateBase no date";
    }

    public void saveRootModelInDB(RootModelOrganization rootModelOrganization) {
        addRegions(rootModelOrganization.getJsonMap().getMapAllRegions());
        addCurrencies(rootModelOrganization.getJsonMap().getMapAllCurrencies());
        addOrgTypes(rootModelOrganization.getJsonMap().getMapAllOrgTypes());
        addCities(rootModelOrganization.getJsonMap().getMapAllCities());
        addOrganization(rootModelOrganization.getListOrganization());
        addCourse(rootModelOrganization.getListOrganization());
        setDateBase(rootModelOrganization);
    }

    public boolean isUpdateDataBase(String dateJson, String dateUpdate) {
        return !dateJson.equals(dateUpdate);
    }
}
