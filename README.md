# WLikeAnim - 简单的点赞动画


<p >
	<a><img src="https://img.shields.io/github/release/wanglu1209/WLikeAnim.svg"/></a>
  	<a><img src="https://img.shields.io/github/last-commit/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/issues/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-closed/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-pr/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-pr-closed/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/forks/wanglu1209/WLikeAnim.svg"/></a>
	<a><img src="https://img.shields.io/github/stars/wanglu1209/WLikeAnim.svg"/></a>
</p>

<div>
<img src="https://raw.githubusercontent.com/wanglu1209/WPopup/master/img/anim_gif.gif" width="200" height="350" />
&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://raw.githubusercontent.com/wanglu1209/WLikeAnim/master/gif/gif.gif" width="250" height="350" />
</div>
## 依赖

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
dependencies {
        implementation 'com.github.wanglu1209:WLikeAnim:lastRelease'
}
```

## 使用

### 普通的动画效果
效果看第一个图 或者[WPopup](https://github.com/wanglu1209/WPopup)（只是简单的缩放）

```Kotlin
WCommonAnim(iv).show()
```

### 类似于掘金的动画效果

```Kotlin
// 第一个参数为点赞的ImageView，第二个参数为想要替换的res
WJueJinLikeAnim.Builder(ImageView, R.mipmap.xxx).create().show()
```

## 解释

**该动画库只有动画，完全不参与逻辑**

类似于下面，所有的逻辑都要自己写：

```Kotlin
var isLiked = false
val likeAnim = WJueJinLikeAnim.Builder(iv, R.mipmap.fd_zan_press).create()
iv.setOnClickListener {
  if(isLiked){
      iv.setImageResource(R.mipmap.fd_zan)
      isLiked = false
  }else{
      iv.setImageResource(R.mipmap.fd_zan_press)
      isLiked = true
      likeAnim.show()
  }
}
```

