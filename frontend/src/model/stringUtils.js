class StringUtils {

    static utf8_to_b64( str ) {
        return window.btoa(unescape(encodeURIComponent( str )));
    }
      
    static b64_to_utf8( str ) {
        return decodeURIComponent(escape(window.atob( str )));
    }
    
    static arrayBufferToBase64( buffer ) {
        var binary = '';
        var bytes = new Uint8Array( buffer );
        var len = bytes.byteLength;
        for (var i = 0; i < len; i++) {
            binary += String.fromCharCode( bytes[ i ] );
        }
        return window.btoa( binary );

    }

    static getFileExt(fileName) {
        
        const lastDot = fileName.lastIndexOf('.')
        
        // const fileName = name.substring(0, lastDot);
        const ext = fileName.substring(lastDot + 1)
        
        
        return ext
    }
}

export default StringUtils;