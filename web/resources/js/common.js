/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function checkValue(form, message) {
    
    var userInput = form[form.id + ":username"];
    
    if (userInput.value ==''){
        alert(message)
        userInput.focus();
        return false;
    } 
    
    return true;
}

