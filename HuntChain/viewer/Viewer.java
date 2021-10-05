package viewer;

import java.util.*;

public class Viewer implements IViewer{


    private Scanner sc = new Scanner(System.in);



    public Viewer(){
        menu();
    }

    /*
     * Decentralized Malware Data for Detection steps.
     * 1. Ask User if they want to add malware Data (a) or Check file (b) (done)
     * 2. if (a), ask user for malware information + hash (done)
     * 3. Data is sent to all connected peers. (skipped step)
     * 4. Mine the block with New data (done)
     * 5. Send new Blockchain.Block to other peers for verification (done)
     * 6. Add block to blockchain (done)
     * 7. if (b), get file's hash using Signature technique malware detection (done)
     * 8. Try to find a match in the blockchain. (done)
     * 9. If found -> Malware otherwise its not (done)
     * 10. Message shown to user (done)
     * */

    public void menu(){

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---------------- Malware Detection Blockchain -------------------------");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("(a) Add Malware Data");
        System.out.println("(b) Check file for Malware");

    }



    public String getPath(){
        System.out.print("Path of the file: ");
        String s = sc.next();


        return s;
    }

    public void menu2(){
        System.out.println("------------------------");
        System.out.println("Verify file using -> ");
        System.out.println("1. Integrity Checker" );
        System.out.println("2. Top and Tail Scanner" );
        System.out.println("------------------------");
    }

    public int getInput2(){
        int s = sc.nextInt();
        return s;
    }

    @Override
    public String getInput() {

        String s = sc.nextLine();

        if (s.toLowerCase().contains("a") || s.toLowerCase().contains("b")){

        } else {
            System.err.print("error");
            sc.close();
        }

        //System.out.println(s);

        return s.toLowerCase();
    }


    @Override
    public HashMap<String,String> malwareDataForMining() {

        //The blockchain should store malware information and signatures of the suspected files

        String fileName;
        String malwareName;
        String familyType;
        String malwareSignatureHA256;
        String malwareSignatureMD5;

        HashMap<String, String> hmap = new HashMap<>();

        System.out.print("Filename : ");
        fileName = sc.nextLine();

        System.out.print("Malware Name : ");
        malwareName = sc.nextLine();

        System.out.print("Malware Type : ");
        familyType = sc.nextLine();

        System.out.print("Malware file signature SHA256 : ");
        malwareSignatureHA256 = sc.nextLine();

        System.out.print("Malware top-tail signature SHA25 : ");
        malwareSignatureMD5 = sc.nextLine();

        //sc.close();

        hmap.put("Family Name", fileName);
        hmap.put("Malware Name", malwareName);
        hmap.put("Malware Type", familyType);
        hmap.put("topTail", malwareSignatureMD5);
        hmap.put("SHA256", malwareSignatureHA256);


        Set set = hmap.entrySet();
        Iterator iterarator = set.iterator();

        while(iterarator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterarator.next();
            System.out.println("-> Here the data that is going to be added:");
            System.out.print("• key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        }
        return hmap;

    }
}
