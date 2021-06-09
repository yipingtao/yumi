<template>
	<view>
		<view class="cu-list menu-avatar">
			<view class="cu-item" v-for="(item,index) in tableList" :key="index" style="min-height: 90px;">
				<view class="cu-avatar  lg">
					<image class="cu-avatar  lg" src="../../static/image/3s.png"></image>
				</view>
				<view class="content">
					<view class="text-grey">订单编号:{{item.code}}<br>
					</view>
					<view class="text-gray text-sm">
						<view class="">
							{{item.createTimeDesc}}
						</view>
						<view class="cu-tag round" @tap="goodsDetail(item)">
							查看详情
						</view>
					</view>
				</view>
				<view class="action" style="width: 30%">
					<view class="text-red text-xs">¥{{item.money}}</view>
					<view class="cu-tag round" v-if="item.state != 6" @tap="changeState(item,6)">
						申请退款
					</view>
					<view class="cu-tag round" v-if="item.state == 3" @tap="changeState(item,4)">
						收货
					</view>
					<view class="cu-tag round" v-if="item.state == 4" @tap="pingjia(item)">
						评价
					</view>
					<view class="cu-tag" v-if="item.state == 6">
						已退款
					</view>
					<view class="cu-tag" v-if="item.state == 5">
						已评价
					</view>
				</view>
			</view>
		</view>

		<!--请填写收货地址-->
		<view class="cu-modal" :class="DialogGoods==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">订单详情</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					<view class="cu-list menu-avatar">
						<view class="cu-item" v-for="(item,index) in orderMain.ordersubList" :key="index">
							<view class="cu-avatar lg">
								<image class="cu-avatar lg" :src="item.goods.photo"></image>
							</view>
							<view class="content">
								<view class="text-grey">{{item.goodName}}</view>
							<!-- 	<view class="text-gray text-sm">
									<text class="text-gr  margin-right-xs">
										{{item.remark}}
									</text>
								</view> -->
							</view>
							<view class="action" style="width: 30%">
								<view class="text-red text-xs">单价:¥{{item.money}}</view>
								<view class="cu-tag round">
									数量:{{item.amount}}
								</view>
							</view>
						</view>
					</view>
					
				</view>
				<view class="cu-bar bg-white justify-end">
					<view style="margin-right: 5upx;"> <text style="color: red;">总金额:¥{{orderMain.money}}</text> </view>
					<view class="action">
						
						<button class="cu-btn line-green text-green" @tap="hideModal">取消</button>
					</view>
				</view>
			</view>
		</view>
		<!--请填写评价-->
		<view class="cu-modal" :class="DialogPingjia==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">请填写评价</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					<view class="cu-form-group">
						<input v-model="orderMain.comment" placeholder="评价内容" name="input"></input>
					</view>
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-green text-green" @tap="hideModal">取消</button>
						<button class="cu-btn bg-green margin-left" @tap="saveComment()">确定评价</button>
		
					</view>
				</view>
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
				DialogGoods: false,
				DialogPingjia:false,
				userObj: {},
				tableList: [{
						id: 1,
						nailArtistId: 1,
						startTimeDesc: '2019-09-01',
						endTimeDesc: "2019-09-01",
						nailArtist: {}
					},
					{
						id: 2,
						nailArtistId: 1,
						startTimeDesc: '2019-09-01',
						endTimeDesc: "2019-09-01",
						nailArtist: {}
					}
				],
				orderMain: {comment:""}
			};
		},
		onReady() {
			let userObj = uni.getStorageSync('userObj');
			this.userObj = userObj
			this.getTableList()
		},
		onShow() {
			let userObj = uni.getStorageSync('userObj');
			this.userObj = userObj
			this.getTableList()
		},
		mounted() {
			this.getTableList()
		},
		methods: {
			goodsDetail: function(item) {
				this.DialogGoods = true
				this.orderMain = item
				
			},
			hideModal:function(){
				this.DialogGoods = false
				this.DialogPingjia = false
			},
			getTableList: function() {
				uni.request({
					url: common.webUrl + 'ordermain/getAllList',
					data: {
						search: {
							userId: this.userObj.id
						}
					},
					method: 'GET',
					dataType: '',
					success: (res) => {
						if (res.data.resCode == '0000') {
							this.tableList = res.data.resultList
						}
					}
				});
			},
			cancleAppoint: function(item) {
				item.state = 2
				uni.request({
					url: common.webUrl + 'appointment/changeState',
					data: item,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "取消成功"
							})
						}
					}
				});
			},
          changeState: function(item,state) {
				item.state = state
				uni.request({
					url: common.webUrl + 'ordermain/changeState',
					data: item,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "操作成功"
							})
						}
					}
				});
			},
			pingjia:function(item){
				this.DialogPingjia = true;
				this.orderMain = item;
				this.orderMain.comment = "";
			},
			saveComment:function(){
				console.log(this.orderMain)
				uni.request({
					url: common.webUrl + 'comment/saveCommnet',
					data: this.orderMain,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							this.changeState(this.orderMain,5)
							this.hideModal()
						}
					}
				});
			}
		}
	}
</script>

<style lang="scss">

</style>
