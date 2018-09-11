## Android 亮屏 熄屏
### 添加依赖
#### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:  

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}  

#### Step 2. Add the dependency

	dependencies {
	    implementation 'com.github.EthanCo:ScreenControl:1.0.1'
	}

### 使用

#### 初始化

	ScreenControl screenControl = new ScreenControl(context,ScreenAdminReceiver.class);  

#### 申请权限  

	if (!screenControl.isAdminActive()) {
        screenControl.openScreenPermission(this, "程序操作需要相应权限，请激活设备管理器");
    }  

#### 请求权限回调

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScreenControl.SCREEN_REQUEST_CODE) {
            if (screenControl.isAdminActive()) {
                Log.i(TAG, "设备已被激活");
            } else {
                Log.i(TAG, "设备没有被激活");
            }
        }
    }

#### 亮屏

	 screenControl.turnOn();  

#### 熄屏

	screenControl.turnOff();  

#### 其他

普通的应用程序，通过在系统设置-》安全-》设备管理-》下激活对应的应用程序，即可让该应用程序具有熄灭屏幕的能力，点亮屏幕则是使用了WakeLock

代码源自[AndroidScreenOnAndOff](https://github.com/sunnythree/AndroidScreenOnAndOff)，进行了相关封装和优化。