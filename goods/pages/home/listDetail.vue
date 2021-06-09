<template>
	<view>
		<view class="banner">
			<image class="banner-img" :src="banner.photo"></image>
			<view class="banner-title">{{banner.name}}/{{banner.phone}}</view>
		</view>
		<view class="addressDiv">
			<!-- <text class="article-text">{{banner.remark}}</text> -->
			地址：{{banner.address}}
			<image @click="goMap()" class="imageClass" src="../../static/image/map.png" mode="aspectFill"></image>
		</view>
		<text class="article-meta">
			门店介绍：{{banner.remark}}<br>
		</text>
		<scroll-view style="color: gray;" scroll-x class="nav text-center" scroll-with-animation :scroll-left="scrollLeft">
			<view class="cu-item" :class="index==TabCur?'text-green cur':''" v-for="(item,index) in tabList" :key="index" @tap="tabSelect"
			 :data-id="index">
				{{item.name}}
			</view>
		</scroll-view>
		<!--商品 -->
		<view v-show="tab1Show">
			<view class="cu-list menu-avatar" style="height: 600upx;overflow: auto;margin-bottom: 30upx;">
				<view class="cu-item" v-for="(item,index) in goodsList" :key="index">
					<view class="cu-avatar lg">
						<image class="cu-avatar lg" :src="item.photo"></image>
					</view>
					<view class="content">
						<view class="text-grey">{{item.name}}</view>
						<view class="text-gray text-sm">
							<text class="text-gr  margin-right-xs">
								{{item.remark}}
							</text>
						</view>
					</view>
					<view class="action" style="width: 30%">
						<view class="text-red text-xs">¥{{item.money}}</view>
						<view class="cu-tag round">
							<image v-if="item.amount!=0" class="imageFont" src="../../static/image/minus.png" @click="minusAmount(index)"></image>
							{{item.amount}}
							<image class="imageFont" src="../../static/image/add.png" @click="addAmount(index)"></image>
						</view>
					</view>
				</view>
			</view>
			<view class="totalWrapper" v-if="totalAmount!=0">
				<button class="round bg-gradual-green" @click="chargeOrder">支付总金额: ¥{{totalAmount}}</button>
			</view>
		</view>

	
		<!--请填写收货地址-->
		<view class="cu-modal" :class="DialogAddress==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">请填写收货地址</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					<view class="cu-form-group">
						<input v-model="orderMain.address" placeholder="收货地址" name="input"></input>
					</view>
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-green text-green" @tap="hideModal">取消</button>
						<button class="cu-btn bg-green margin-left" @tap="saveOrder()">确定支付</button>

					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import common from '../../common/common.js';
	import utils from '../../common/util.js';
	import ruiDatePicker from '@/components/rattenking-dtpicker/rattenking-dtpicker.vue';
	export default {
		components: {
			ruiDatePicker
		},
		data() {
			return {
				appointment: {
					userId: 0,
					nailArtistId: 0,
					state: 1,
					startTime: 0,
					endTime: 0
				},
				startTime: '2020-01-01 00:00',
				endTime: '2020-01-01 23:59',
				userObj: {
					id: 0,
					userName: ''
				},
				DialogAddress: false,
				dataTimeShow: false,
				banner: {},
				TabCur: 0,
				scrollLeft: 0,
				tabList: [{
						id: '0',
						name: '商品'
					}
				],
				goodsList: [{
						id: 1,
						name: '商品1',
						remark: '商品描述',
						money: "20",
						amount: 0
					},
					{
						id: 2,
						name: '商品2',
						remark: '商品描述',
						money: "20.5",
						amount: 0
					}
				],
				nailArtistList: [{
						id: 1,
						name: '商品1',
						remark: '商品描述',
						photo: '',
						phone: ''

					},
					{
						id: 2,
						name: '商品2',
						remark: '商品描述',
						photo: '',
						phone: ''
					}
				],
				modalName: null,
				listTouchDirection: null,
				ListTouchStart: 0,
				tab1Show: true,
				tab2Show: false,
				totalAmount: 0,
				orderMain: {
					address: "",
					money: 0,
					state: 2,
					storeId: 0,
					userId: 0,
					storeName: '',
					userName: '',
					goodsList: []
				}
			}
		},
		onLoad(event) {
			// 目前在某些平台参数会被主动 decode，暂时这样处理。
			try {
				this.banner = JSON.parse(decodeURIComponent(event.detailDate));
			} catch (error) {
				this.banner = JSON.parse(event.detailDate);
			}
			uni.setNavigationBarTitle({
				title: this.banner.name
			});

		},
		onReady() {
			let userObj = uni.getStorageSync('userObj');
			this.userObj = userObj
			this.totalAmountAdd()
		},
		mounted() {
			this.getGoodsLit()
			this.getNailArtistLit()
			this.totalAmountAdd()
		},
		methods: {
			saveDataTime: function() { //确定预约
				this.appointment.startTime = utils.dataToLong(this.startTime)
				this.appointment.endTime = utils.dataToLong(this.endTime)
				this.appointment.userId = this.userObj.id
				uni.request({
					url: common.webUrl + 'appointment/addAppointment',
					data: this.appointment,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "预约成功"
							})
						}else{
							uni.showToast({
								title: "该时间段已经被人预约",
								icon: "none"
							})
							
						}
					}
				});
				
			},
			cancleDataTime: function() {
				this.dataTimeShow = false
			},
			yuyue: function(id) {
				this.appointment.nailArtistId = id
				this.dataTimeShow = true
			},
			bindStartChange: function(value) {
				this.startTime = value;
			},
			bindEndChange: function(value) {
				this.endTime = value;
			},

			bindCancel: function() {

			},
			getNailArtistLit: function() {
				uni.request({
					url: common.webUrl + 'nailArtist/getAllList',
					data: {
						search: {
							"storeId": this.banner.id
						}
					},
					method: 'GET',
					dataType: '',
					success: (res) => {
						if (res.data.resCode == '0000') {
							this.nailArtistList = res.data.resultList
						}
					}
				});
			},
			getGoodsLit: function() {
				uni.request({
					url: common.webUrl + 'goods/getAllList',
					data: {
						search: {
							"storeId": this.banner.id
						}
					},
					method: 'GET',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							this.goodsList = res.data.resultList
							this.goodsList.forEach(item => {
								item.amount = 0
							})
						}
					}
				});
			},
			goMap: function(e) {
				uni.navigateTo({
					url: "./map?detailDate=" + encodeURIComponent(JSON.stringify(this.banner))
				})
			},
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				if (this.TabCur == 0) {
					this.tab1Show = true
					this.tab2Show = false
				} else {
					this.tab1Show = false
					this.tab2Show = true
				}
			},

			// ListTouch触摸开始
			ListTouchStarts(e) {
				this.listTouchStart = e.touches[0].pageX
			},

			// ListTouch计算方向
			ListTouchMove(e) {
				this.listTouchDirection = e.touches[0].pageX - this.listTouchStart > 0 ? 'right' : 'left'
			},
			// ListTouch计算滚动
			ListTouchEnd(e) {
				if (this.listTouchDirection == 'left') {
					this.modalName = e.currentTarget.dataset.target
				} else {
					this.modalName = null
				}
				this.listTouchDirection = null
			},
			addAmount: function(index) {
				console.log(index)
				let amount = this.goodsList[index].amount + 1
				this.goodsList[index].amount = amount
				this.totalAmountAdd();
			},
			minusAmount: function(index) {
				let amount = this.goodsList[index].amount - 1
				this.goodsList[index].amount = amount > 0 ? amount : 0
				this.totalAmountAdd()
			},

			totalAmountAdd: function() {
				let totalAmount = 0
				
				for (let item of this.goodsList) {
					console.log(item.amount+"--------"+item.money)
					totalAmount = totalAmount + item.amount * item.money
				}
				this.totalAmount = totalAmount
			},
			chargeOrder: function() {
				this.DialogAddress = true

			},
			saveOrder: function() {

				this.DialogAddress = false
				this.orderMain.money = this.totalAmount
				this.orderMain.storeId = this.banner.id
				this.orderMain.storeName = this.banner.name
				this.orderMain.userId = this.userObj.id
				this.orderMain.userName = this.userObj.name
				this.orderMain.goodsList = this.goodsList
				uni.request({
					url: common.webUrl + 'ordermain/saveOrder',
					data: this.orderMain,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "支付成功"
							})
						}
					}
				});

			},
			hideModal: function() {
				this.DialogAddress = false
				this.dataTimeShow = false
			}
		}
	}
</script>

<style>
	view {
		font-size: 28upx;
		line-height: 1.8;
	}

	.dataTimeWrapper {
		margin: 10upx 10upx;
	}

	.banner {
		height: 360upx;
		overflow: hidden;
		position: relative;
		background-color: #ccc;
	}

	.banner-img {
		width: 100%;
	}

	.banner-title {
		max-height: 84upx;
		overflow: hidden;
		position: absolute;
		left: 30upx;
		bottom: 30upx;
		width: 90%;
		font-size: 32upx;
		font-weight: 400;
		line-height: 42upx;
		color: white;
		z-index: 11;
	}

	.article-meta {
		padding: 20upx 40upx;
		color: gray;
	}

	.article-text {
		font-size: 26upx;
		line-height: 50upx;
		margin: 20upx;
	}

	.article-author,
	.article-time {
		font-size: 30upx;
	}

	.article-content {
		padding: 0 30upx;
		overflow: hidden;
		font-size: 30upx;
		margin-bottom: 30upx;
	}

	.addressDiv {
		display: flex;
		width: 100%;
		flex-direction: row;
		margin: 0 40upx;
		align-items: center;
		color: gray;
	}

	.imageClass {
		width: 30upx;
		height: 30upx;
		left: 20upx;
	}

	.imageFont {
		width: 30upx;
		height: 30upx;
		margin: 0 5upx;
	}

	.totalWrapper {
		display: flex;
		flex-direction: row;
		text-align: center;
		align-items: center;
		justify-items: center;
		position: fixed;
		bottom: 0;
		/* background-color: #39B54A; */
		width: 100%;
		border-radius: 30upx;
	}

	.rui-head {
		font-size: 4vw;
		height: 10vw;
		line-height: 10vw;
		padding: 0 4vw;
		text-align: left;
	}

	.rui-flex {
		display: -webkit-flex;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 4vw;
		padding: 0 4vw;
	}
</style>
