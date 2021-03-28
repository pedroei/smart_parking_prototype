package pt.ipvc.smartparkingprototype.data

import pt.ipvc.smartparkingprototype.R
import pt.ipvc.smartparkingprototype.models.Coordinates
import pt.ipvc.smartparkingprototype.models.ParkingLotItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceSection

class ParkingLotData {

    var reservedSpace: ParkingSpaceItem? = null;

    fun setDataList(): ArrayList<ParkingLotItem> {

        /* Slots */
        val slotsList1: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList1.add(ParkingSpaceItem(1, 1,false, 1))

        val slotsList2: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList2.add(ParkingSpaceItem(2, 1, false, 5))

        val slotsList3: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList3.add(ParkingSpaceItem(3, 1, false, 2))

        val slotsList4: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList4.add(ParkingSpaceItem(4, 1, false, 3))

        val slotsList5: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList5.add(ParkingSpaceItem(5, 1, false, 4))
        val slotsList6: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList6.add(ParkingSpaceItem(6, 1,false, 7))

        val slotsList7: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList7.add(ParkingSpaceItem(7, 1,false, 8))

        val slotsList8: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList8.add(ParkingSpaceItem(8, 1,false, 6))
        slotsList8.add(ParkingSpaceItem(9, 2,false, 6))

        /* Sections */
        val sectionList1: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList1.add(ParkingSpaceSection(1, "A", slotsList1, 4))
        sectionList1.add(ParkingSpaceSection(2, "B", slotsList3, 4))

        val sectionList2: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList2.add(ParkingSpaceSection(3, "A", slotsList4, 2))
        sectionList2.add(ParkingSpaceSection(4, "B", slotsList5, 2))
        sectionList2.add(ParkingSpaceSection(5, "C", slotsList2, 2))

        val sectionList3: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList3.add(ParkingSpaceSection(6, "A", slotsList8, 1))
        sectionList3.add(ParkingSpaceSection(7, "B", slotsList6, 1))

        val sectionList4: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList4.add(ParkingSpaceSection(8, "A", slotsList7, 3))

        /* Coordinates */
        val coordinates_1_maio = Coordinates(41.69546913970562, -8.82892591713366)
        val coordinates_campo_agonia = Coordinates(41.691572004150586, -8.836688477030924)
        val coordinates_gil_eanes = Coordinates(41.68979067792167, -8.830456039883426)
        val coordinates_barga_parque = Coordinates(41.55766747202138, -8.407030445902803)

        /* Lots */
        val arrayList: ArrayList<ParkingLotItem> = ArrayList()
        arrayList.add(ParkingLotItem(1, "1º de Maio", R.drawable.pe1, "Viana do Castelo", "Open 24 hours", sectionList3, coordinates_1_maio))
        arrayList.add(ParkingLotItem(2, "Campo da Agonia", R.drawable.pe2, "Viana do Castelo", "Open 24 hours", sectionList2, coordinates_campo_agonia))
        arrayList.add(ParkingLotItem(3, "Gil Eanes", R.drawable.pe3, "Viana do Castelo", "Open 24 hours", sectionList4, coordinates_gil_eanes))
        arrayList.add(ParkingLotItem(4, "Braga Parque", R.drawable.pe1, "Braga", "Open 24 hours", sectionList1, coordinates_barga_parque))


        return arrayList
    }

    fun getParkingLotById(lotId: Int): ParkingLotItem {
        val arrayList:ArrayList<ParkingLotItem> = setDataList()

        return arrayList.find { p -> p.id == lotId }!!
    }

    fun getSectionById(sectionId: Int): ParkingSpaceSection? {
        val arrayList:ArrayList<ParkingLotItem> = setDataList()

        arrayList.forEach {
            it.sections.forEach {
                if (it.id == sectionId) {
                    return it
                }
            }
        }
        return null
    }

    fun reserveSlot(space: ParkingSpaceItem): Boolean {
        if (this.reservedSpace == null) {
            this.reservedSpace = space
            space.reserved = true
            return true
        }
        return false
    }

    fun getReservedSlot(): ParkingSpaceItem {
        return this.reservedSpace!!
    }

}