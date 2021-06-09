<template>
	<view>
		<view class="cu-list menu-avatar">
			<view class="cu-item" v-for="(item,index) in tableList" :key="index">
				<view class="cu-avatar round lg">
					<image class="cu-avatar round lg" :src="item.nailArtist.photo"></image>
				</view>
				<view class="content">
					<view class="text-grey">{{item.nailArtist.name}}</view>
					<view class="text-gray text-sm">
						<text class="cuIcon-infofill text-grey  margin-right-xs">
							{{item.startTimeDesc}}<br>
							至{{item.endTimeDesc}}
						</text>
					</view>
				</view>
				<view class="action" style="width: 30%">
					<view class="text-grey text-xs">{{item.state==2?'取消预约':'已预约'}}</view>
					<view class="cu-tag round" @tap="cancleAppoint(item)" v-if="item.state==1">
						取消预约
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
				]
			};
		},
		onReady() {
			let userObj = uni.getStorageSync('userObj');
			this.userObj = userObj
		},
		onShow() {
			this.getTableList()
		},
		mounted() {
			this.getTableList()
		},
		methods: {
			getTableList: function() {
				uni.request({
					url: common.webUrl + 'appointment/getAllList',
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

		}
	}
</script>

<style lang="scss">

</style>
