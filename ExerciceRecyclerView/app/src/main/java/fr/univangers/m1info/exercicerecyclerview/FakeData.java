package fr.univangers.m1info.exercicerecyclerview;

public final class FakeData {

    /**
     * Cette méthode retourne une liste de données de test
     *
     * @return Une liste de tâches
     */
    public static TimeTableAdapter.SlotDescription[] get_timetable() {
        return new TimeTableAdapter.SlotDescription[] {
                new TimeTableAdapter.SlotDescription("26/09/2019 08:00", "O. Goudet", "Design patterns"),
                new TimeTableAdapter.SlotDescription("23/09/2019 09:30", "B. Da Mota", "Réseau"),
                new TimeTableAdapter.SlotDescription("24/09/2019 09:00", "O. Goudet", "Design patterns"),
                new TimeTableAdapter.SlotDescription("25/09/2019 09:00", "P. Montembault", "Communication"),
                new TimeTableAdapter.SlotDescription("23/09/2019 13:30", "A. Todoskoff", "Organisation et conduite de projets"),
                new TimeTableAdapter.SlotDescription("25/09/2019 14:00", "V. Barichard, B Da Mota, L. Garcia, ...", "Concrétisation disciplinaire"),
                new TimeTableAdapter.SlotDescription("26/09/2019 14:00", "P. Torres", "Anglais"),
                new TimeTableAdapter.SlotDescription("27/09/2019 14:00", "A. Goeffon", "Optimisation linéaire"),
                new TimeTableAdapter.SlotDescription("24/09/2019 13:30", "J-M. Chantrein", "Création, déploiement et exécution de conteneurs logiciels"),
                new TimeTableAdapter.SlotDescription("27/09/2019 09:00", "E. Monfroy", "Introduction à la résolution de problèmes")
        };
    }
}
