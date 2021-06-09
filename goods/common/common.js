const webUrl = 'http://192.168.207.1:8088/';  
const now = Date.now || function () {  
    return new Date().getTime();  
};  
const isArray = Array.isArray || function (obj) {  
    return obj instanceof Array;  
};  

export default {  
    webUrl,  
    now,  
    isArray  
}  