<?php

namespace App\Http\Controllers\TrackerAPI;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Services\MovementService;
use Illuminate\Http\File;
use Illuminate\Support\Facades\Storage;
use DB;
class TrackerApiController extends Controller
{     

    public function __construct()
    {
        $this->movementService = new MovementService();
    }

    public function supervisor_challan(Request $request){
        $user_id = $request->userid;
        $type = $request->type;
        $challan_no_list =  $this->movementService->get_supervisor_challan($user_id , $type); 
        if(empty($challan_no_list)){
          return "null";
        }else{
          return json_encode($challan_no_list);
        }
        
    }
 

    public function scaffolder_challan(Request $request){
      $user_id = $request->userid;
      $type = $request->type;
      $challan_no_list = $this->movementService->get_scaffolder_challan($user_id , $type); 
      if(empty($challan_no_list)){
        return "null";
      }else{
        return json_encode($challan_no_list);
      }
    }
  
    public function storelocation(Request $request ){
      $challanid = $request->challanid;
      $longitude = $request->longitude;
      $lattitude = $request->lattitude;
      $location = [
        'lat' => $lattitude,
        'lng' => $longitude
      ];

      $point = DB::table('challan_scaffolder_rel')
      ->where('challan_no', $challanid)
      ->update($location);

      return "$longitude:$lattitude"; 
    }

    public function challanverify(Request $request ){
     // $photourl = $request->photo;
      $challanid = $request->challanid;
      $action = $request->action;
      $Role = $request->userRole;
      $file_name = null;

      if("material_verification" == $request->action ){
        $file_name =  $_FILES['myFile']['name'];
        move_uploaded_file($_FILES["myFile"]["tmp_name"], $_FILES["myFile"]["name"]);
      }else{
        $photo = $request->photo;  
        $file_name = "amamamama.jpg";
        file_put_contents($file_name,base64_decode($photo));
      }
 

   //   $file_name = basename($photourl); 

   //   $file = file_get_contents($photourl);

      $uploadSuccess = true; 

    //   if(env('ALLOW_S3')) {
    //     $uploadSuccess = Storage::put('challans/'.$file_name, $file);
    //  }
      
     if($uploadSuccess)
     {
      if("Supervisor" == $Role){
        $affected = DB::table('challan_supervisor_rel')
        ->where('challan_no', $challanid)
        ->update([$action => 'challans/'.$file_name]);

        $this->movementService->update_supervisor_table_status(); 
      }
      if("Scaffolder"== $Role){
        $affected = DB::table('challan_scaffolder_rel')
               ->where('challan_no', $challanid)
              ->update([$action => 'challans/'.$file_name]);

        $this->movementService->update_scaffolder_table_status(); 
      }
      return $file_name ;
     }
      return "FAILED";
    }

    public function Steps_verification(Request $request ){
      $challan_no = $request->challanid; 
      $Role = $request->role;
      $challan_details = $this->movementService->get_steps_to_verify($challan_no,$Role); 
      return json_encode($challan_details);
    }

}

// public function loginauth(Request $request ){
//   $email = $request->user;
//   $password = $request->password;
//   // try{
//   //         $result =$this->response($this->loginProxy->attemptLogin($email, $password));
  
//   //         if($result->getStatusCode()==200){
//             $user_data =  $this->userService->get_auth_appuser($email);
//             if(empty($user_data)){
//               return "0";
//             }else{
//               return json_encode($user_data);
//             }
//   //         }
//   // else return 0;
  
//   //  }
//   // catch(\Exception $e)
//   // {
//    return 0;
//   // }
// }
