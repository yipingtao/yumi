<template>
	<view>
		<view class="content">
			<view class="avatarWrapper">
				<view class="avatar">
					<image class="img" src="../../static/image/5.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="form" style="color: #8F8F94;">
				<view class="cu-form-group">
					<view class="title">用户名</view>
					<input placeholder="请输入用户名" v-model="userObj.loginName" name="loginName" ></input>
				</view>
				<view class="cu-form-group">
					<view class="title">密码</view>
					<input placeholder="请输入密码" v-model="userObj.password" name="password"></input>
				</view>
			</view>
			<view class="buttonWrapper">
				<button  class="cu-btn round bg-gradual-green buttonClass" @tap="saveUser()">修改</button>
				<button  class="cu-btn round bg-gradual-green buttonClass" @tap="quit()">退出</button>
			</view>
		</view>
	</view>
</template>

<script>
	import common from '../../common/common.js';
	import utils from '../../common/util.js';
	export default {
		data() {
			return {
				userObj: {}
			};
		},
		onReady() {
			
		},
		onShow() {
			let userObj = uni.getStorageSync('userObj');
			this.userObj = userObj
		},
		methods:{
			quit:function(){
				uni.navigateTo({ //跳转到非TAB页面
				    url: '../login/login'
				});
			},
			saveUser:function(){
				uni.request({
					url: common.webUrl + 'user/editUser',
					data: this.userObj,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "操作成功"
							})
						}
						uni.setStorage({
							key: 'userObj',
							data: this.userObj,
							success: function () {
								console.log(JSON.stringify(this.userObj));
							}
						});
					}
				});
			}
		}
	}
</script>

<style lang="scss">
.content {
		width: 100vw;
		height: 100vh;
	}

	.avatarWrapper {
		height: 30vh;
		width: 100vw;
		display: flex;
		justify-content: center; //属性定义了项目在主轴上的对齐方式
		align-items: flex-start; //属性定义项目在交叉轴上如何对齐
	}

	.avatar {
		width: 200upx;
		height: 200upx;
		// overflow: hidden;
	}

	.avatar .img {
		width: 100%;
	}
	
	.buttonWrapper {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		margin-top: 20upx;
	}
	.buttonClass{
		margin: 5upx 5upx;
	}
</style>
