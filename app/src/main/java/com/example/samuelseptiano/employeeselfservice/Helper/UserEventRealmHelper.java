package com.example.samuelseptiano.employeeselfservice.Helper;

public class UserEventRealmHelper {
//    private static final String TAG = "UserRealmHelper";
//
//
//    private Realm realm;
//    private RealmResults<UserEventsRealm> realmResult;
//    public Context context;
//
//    /**
//     * constructor untuk membuat instance realm
//     *
//     * @param context
//     */
//    public UserEventRealmHelper(Context context) {
//        realm = Realm.getInstance(Realm.getDefaultConfiguration());
//        this.context = context;
//    }
//
//
//    /**
//     * menambah data
//     *
//     * @param //title
//     * @param //description
//     */
//    public void addArticle(User_Events ue) {
//        User_Events userEvents = new User_Events();
//        UserEventsRealm uer = new UserEventsRealm();
//        Home hm = new Home();
//        Random rand = new Random();
//
//        uer.setId((int)  (System.currentTimeMillis() / 1000)+rand.nextInt(1000000) + 1);
//        uer.setDateJoin(ue.getDateJoin());
//        uer.setNewsDetail(ue.getUserEvents().getEventDesc());
//        uer.setNewsName(ue.getUserEvents().getEventName());
//        uer.setNewsPoster(ue.getUserEvents().getEventImage());
//        uer.setNewsId(ue.getUserEvents().getEventId());
//        uer.setUserId(ue.getId_user());
//        uer.setStatus(ue.getStatus());
//
//        realm.beginTransaction();
//        realm.copyToRealm(uer);
//        realm.commitTransaction();
//
//
//        showLog("Added ; " + uer.getNewsName());
//        showToast(uer.getNewsName() + " berhasil disimpan.");
//    }
//
//
//    /**
//     * method mencari semua article
//     */
//    public ArrayList<UserEventsRealm> findAllArticle() {
//        ArrayList<UserEventsRealm> data = new ArrayList<>();
//
//
//        realmResult = realm.where(UserEventsRealm.class).findAll();
//        realmResult.sort("id", Sort.DESCENDING);
//        if (realmResult.size() > 0) {
//            showLog("Size : " + realmResult.size());
//            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();
//
//            for (int i = 0; i < realmResult.size(); i++) {
//                String userId,newsId,dateJoin,newsPoster,newsName,status,newsDesc;
//                int id = realmResult.get(i).getId();
//                userId = realmResult.get(i).getUserId();
//                dateJoin = realmResult.get(i).getDateJoin();
//                newsId = realmResult.get(i).getNewsId();
//                newsDesc = realmResult.get(i).getNewsDetail();
//                newsPoster = realmResult.get(i).getNewsPoster();
//                newsName = realmResult.get(i).getNewsName();
//                status = realmResult.get(i).getStatus();
//                data.add(new UserEventsRealm(id,userId,newsId,dateJoin,newsName,newsPoster,status,newsDesc));
//            }
//
//
//        } else {
//            showLog("Size : 0");
//            showToast("Database Kosong!");
//        }
//
//        return data;
//    }
//
//
//    /**
//     * method update article
//     *
//     * @param id
//     * @param //title
//     * @param //description
//     */
////    public void updateArticle(int id, UserEvent ue) {
////        realm.beginTransaction();
////        UserEvents user = realm.where(UserEvents.class).equalTo("id", id).findFirst();
////        user.setId((int) (System.currentTimeMillis() / 1000));
////
////        user.setUserId(ue.getId_user());
////        user.setNewsId(ue.getId_news());
////        user.setNewsPoster(ue.getNewsPoster());
////        user.setNewsName(ue.getNewsName());
////        user.setStatus(ue.getStatus());
////        user.setNewsDetail(ue.getNewsDetail());
////        user.setDateJoin(ue.getDate_join());
////
////
////        realm.commitTransaction();
////        showLog("Updated : " + user.getUserId());
////
////        showToast(user.getUserId() + " berhasil diupdate.");
////    }
//
//
//    /**
//     * method menghapus article berdasarkan id
//     *
//     * @param id
//     */
//    public void deleteData(String id) {
//        RealmResults<UserEventsRealm> dataDesults = realm.where(UserEventsRealm.class).equalTo("id", id).findAll();
//        realm.beginTransaction();
//        //dataDesults.remove(0);
//        //dataDesults.removeLast();
//        //dataDesults.clear();
//        dataDesults.deleteAllFromRealm();
//        realm.commitTransaction();
//
//        showToast("Hapus data berhasil.");
//    }
//
//
//    public void deleteAllData() {
//        RealmResults<UserEventsRealm> dataDesults = realm.where(UserEventsRealm.class).findAll();
//        realm.beginTransaction();
//        dataDesults.deleteAllFromRealm();
//        realm.commitTransaction();
//
//        showToast("Hapus Semua data berhasil.");
//    }
//
//
//    /**
//     * membuat log
//     *
//     * @param s
//     */
//    private void showLog(String s) {
//        Log.d(TAG, s);
//
//    }
//
//    /**
//     * Membuat Toast Informasi
//     */
//    private void showToast(String s) {
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//    }
}
