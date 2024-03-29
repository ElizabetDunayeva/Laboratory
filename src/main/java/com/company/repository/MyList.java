package com.company.repository;

import com.company.InjectException;
import com.company.Injector;

import com.company.sort.ISort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * class for storaging information about persons
  *  massiv Persons
  * count - number of Persons
 */
@SuppressWarnings("unchecked")

public class MyList<T> implements IRepository<T> {
    private T[] Persons ;
    private int count;
    private ISort<T> sorter;


    public T[] getPersons() {

        return Persons;
    }
    public int getCount(){
        return count;
    }

    /**
     * Constructer, create list with length =1
     */
    public MyList() {

     Persons = (T[])new Object [1];
     this.count = 0;

    }

    /**

    /*public T getin( int id)   {
        int k = 0;
        for (int i = 0; i < count; i++) {
            if (Persons[i].getId== id) {
                k += i + 1;
            }
        }
        if (k == 0) {
            throw new IllegalArgumentException("Такого обьекта нет!");
        }
       // System.out.println("Информация по запрошенному индекcу"+"  "+id);
        getPersonsDataindex(k - 1);
        return Persons[k - 1];
    }*/

    public T get(int i) {
        return (T)Persons[i];
    }
    /**
     * shows all information about all persons in the list
     */
    public void getPersonsData() {
        for (int i = 0; i < count; i++) {

            System.out.println(Persons[i].toString());

            System.out.println("-----------------------------------------------");
                    }


    }

    /**
     * shows all information about person with index i
     * @param i is index person in the massiv
     */
    public void getPersonsDataindex(int i) {

           if (i > count + 1) {
               throw new IllegalArgumentException("Такого обьекта нет!");
           }
            System.out.println(Persons[i]);

            System.out.println("-----------------------------------------------");



    }

    /**
     * Add new person to a list,extends length of list into 2 times
     * @param p1 person which need to be added
     * @return new list  with added person
     */
    @Override
    public void add(T p1) {

        int col = Persons.length;
        count++;
        if (count > Persons.length) {
            T newPersons[] = (T[])new Object [2*col] ;

            System.arraycopy(Persons, 0, newPersons, 0, Persons.length);
            newPersons[count - 1] = p1;
            Persons = newPersons;


        } else {
            Persons[count - 1] = p1;


        }


    }

    /**
     * delete person with a set  identification value and throws exception if there is not such a person with ID=id
     * @param id identification value of person (ID)
     * @return new list of persons without person with ID = id
     */
    public T delete(int id) {

        int col = Persons.length;
        count--;
        T newPersons[] = (T[])new Object [col] ;

        System.arraycopy(Persons, 0, newPersons, 0, id );
        System.arraycopy(Persons, id+1, newPersons,  id , Persons.length - id - 1);
        Persons = newPersons;



      return Persons[id ];


    }

    /**
     * sort by method puzirka. from bigger to smaller
     * @param comp defines field on what would be list sorted
     */
    public void sortBy(Comparator<T> comp)  {
        try {
        Injector.inject(this);
        } catch(InjectException e) {

        }
        sorter.sort(this,comp);

    }



    //public IPerson set(int index, IPerson person);

     public void add(int index, T person){

         int col = Persons.length;
         count++;
         if (count > Persons.length) {
           T newPersons[]  = (T[])new Object [2*col] ;
             System.arraycopy(Persons, 0, newPersons, index-1, index);
             newPersons[index] = person;
             System.arraycopy(Persons, index, newPersons, index+1, Persons.length-index);
             Persons = newPersons;


         } else {
             T newPersons[] = (T[])new Object [Persons.length] ;
             System.arraycopy(Persons, 0, newPersons, index-1, index);
             newPersons[index] = person;
             System.arraycopy(Persons, index, newPersons, index+1, Persons.length-index);
             Persons = newPersons;


         }

     }
    public List<T> toList(){
        ArrayList<T> list = new ArrayList<T>();
        for(int i =0;i<count;i++)
            list.add(Persons[i]);
        return list;
    }

    public T set(int index, T person){
        T temp = Persons[index];
        Persons[index] = person;
        return temp ;
    }
    public IRepository searchBy(Predicate<T> predicate){
        MyList res = new MyList();
        for (T val : Persons) {
            if (predicate.test(val))
                res.add(val);
        }
        return res;
    }
}
