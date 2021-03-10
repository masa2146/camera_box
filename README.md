## YÜKLEME İŞLEMİ
Camera Box uygulamasının Orange PI'da çalışması için bazı bağımlılıklara ihtiyacı vardır.
Bunlar;
* [chromium-browser 87.0.4280.66](http://ports.ubuntu.com/pool/universe/c/chromium-browser/chromium-browser_87.0.4280.66-0ubuntu0.18.04.1_armhf.deb)
* [chromedriver 87.0.4280.66](https://github.com/electron/electron/releases/download/v11.2.3/chromedriver-v11.2.3-linux-armv7l.zip)
* [chromium-codecs-ffmpeg-extra 87.0.4280.66](http://ports.ubuntu.com/pool/universe/c/chromium-browser/chromium-codecs-ffmpeg-extra_87.0.4280.66-0ubuntu0.18.04.1_armhf.deb)

bu bağımlılıklar dahili olarak **setup_browser** içerisinde yer almaktadır.

Eğer chromium-browser  yükleme sırasında aşağıdaki gibi bir hata alırsanız 
```shell script
pkg: dependency problems prevent configuration of chromium-browser:
 chromium-browser depends on chromium-codecs-ffmpeg-extra (= 87.0.4280.66-0ubuntu0.18.04.1) | chromium-codecs-ffmpeg (= 87.0.4280.66-0ubuntu0.18.04.1); however:
  Version of chromium-codecs-ffmpeg-extra on system is 88.0.4324.150-0ubuntu0.18.04.1.
  Package chromium-codecs-ffmpeg is not installed.
```
**chromium-codecs-ffmpeg-extra** paketini yüklemeniz gerekir.  

## KULLANIMI
Camera Box uygulamasının bu versiyonu aşağıdaki end-pointler üzerinde işlem yapmaktadır.

* http://localhost:8080/camera/list : **GET** metoduyla çalışır. Tüm kamera listesini getirir.
* http://localhost:8080/camera :   **POST** metoduyla çalışır. Yeni bir kamera ekleme işleminde kullanılır.
<br>Örnek veri:

```json
{
  "ip" : "192.168.1.222",
  "port" : 1212,
  "username" : "masa111",
  "password" : "8ASD871HG"
}
```
* http://localhost:8080/camera/<id> : **DELETE** metoduyla çalışır. Kamera verisi silme işlemi.
* http://localhost:8080/import :  **POST** White list import etme işleminde kullanılır. 
<br>Örnek veri: 
<br>
 
```json
[
  {
    "AuthorityList": "OpenGate",
    "BeginTime": "2017-01-01 00:00:00",
    "CancelTime": "2037-12-31 00:00:00",
    "Master Of Car": "Jake",
    "PlateNumber": "001AAA000"
  },
  {
    "AuthorityList": "OpenGate",
    "BeginTime": "2017-01-01 00:00:00",
    "CancelTime": "2037-12-31 00:00:00",
    "Master Of Car": "Jake2",
    "PlateNumber": "001BBB000"
  }
]
``` 

