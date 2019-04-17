package com.logan20apps.egarden

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray

/**
 * Created by kwasi on 17/02/2018.
 */
class FarmerInventoryFragment : Fragment(){

    var filterenabled: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_inventory,container,false)
        val list = view.findViewById<ListView>(R.id.lv_farmerinventory)
        registerForContextMenu(list)
        list.adapter=FarmerInventoryAdapter(this.context!!,filterenabled)

        if (filterenabled){
            view.findViewById<ImageButton>(R.id.ib_addinventory).visibility=View.INVISIBLE
        }
        view.findViewById<ImageButton>(R.id.ib_addinventory).setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fl_content,FarmerAddInventory()).commit()
        }
        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = activity!!.menuInflater
        inflater.inflate(R.menu.context_menu,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when(item.itemId){
            R.id.addtomarket->{
                val user = FirebaseAuth.getInstance().currentUser
                User.inventory[info.position].isInMarket=true
                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("inventory").child(User.inventory[info.position].key).setValue(User.inventory[info.position].toJson().toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(context,"Successfully added to market",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context,"An error has occurred",Toast.LENGTH_SHORT).show()
                    }
                }

                val newarr = JSONArray()
                User.inventory
                        .filter { it.isInMarket }
                        .forEach { newarr.put(it.toJson().toString()) }

                val map = HashMap<String,String>()
                map.put(FirebaseAuth.getInstance().currentUser!!.uid,newarr.toString())

                FirebaseDatabase.getInstance().reference.child("market").setValue(map)
                return true
            }
            R.id.removefrommarker->{
                val user = FirebaseAuth.getInstance().currentUser
                User.inventory[info.position].isInMarket=false
                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("inventory").child(User.inventory[info.position].key).setValue(User.inventory[info.position].toJson().toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(context,"Successfully removed from market",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context,"An error has occurred",Toast.LENGTH_SHORT).show()
                    }
                }

                val newarr = JSONArray()
                User.inventory
                        .filter { it.isInMarket }
                        .forEach { newarr.put(it.toJson().toString()) }

                val map = HashMap<String,String>()
                map.put(FirebaseAuth.getInstance().currentUser!!.uid,newarr.toString())

                FirebaseDatabase.getInstance().reference.child("market").setValue(map)

                return true
            }
            R.id.delete->{
                val user = FirebaseAuth.getInstance().currentUser
                User.inventory[info.position].isInMarket=false
                FirebaseDatabase.getInstance().reference.child("users").child(user?.uid!!).child("inventory").child(User.inventory[info.position].key).removeValue().addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(context,"Successfully removed",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context,"An error has occurred",Toast.LENGTH_SHORT).show()
                    }
                }

                val newarr = JSONArray()
                User.inventory
                        .filter { it.isInMarket }
                        .forEach { newarr.put(it.toJson().toString()) }

                val map = HashMap<String,String>()
                map.put(FirebaseAuth.getInstance().currentUser!!.uid,newarr.toString())

                FirebaseDatabase.getInstance().reference.child("market").setValue(map)
                return true
            }
            else->{
                return super.onContextItemSelected(item)
            }
        }

    }

}