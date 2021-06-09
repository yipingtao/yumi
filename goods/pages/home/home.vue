<template>
	<view>
		<view>
			<swiper class="screen-swiper square-dot" :indicator-dots="true" :circular="true" :autoplay="true" interval="5000"
			 duration="500">
				<swiper-item v-for="(item,index) in swiperList" :key="index">
					<image :src="item.url" mode="aspectFill" v-if="item.type=='image'"></image>
				</swiper-item>
			</swiper>
		</view>
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value,key) in listData" :key="key" @click="goDetail(value)">
				<view class="uni-media-list">
					<image class="uni-media-list-logo" :src="value.photo"></image>
					<view class="uni-media-list-body">
						<view class="uni-media-list-text-top">{{value.name}}/{{value.phone}}</view>
						<view class="uni-media-list-text-bottom">{{value.remark}}</view>
						<view class="uni-media-list-text-bottom">地址:{{value.address}}</view>
						<view class="uni-media-list-text-bottom">{{value.phone}}</view>
					</view>
				</view>
			</view>
		</view>

	</view>

</template>

<script>
	import common from '../../common/common.js';
	export default {
		data() {
			return {
				listData: [{
						photo: '/static/swiper/0.jpg',
						name: '门店一',
						phone: '021-9999999',
						remark: '门店描述:化妆品专卖店',
						address: '上海市徐家汇百盛大厦一楼'
					},
					{
						photo: '/static/swiper/1.jpg',
						name: '门店二:化妆品专卖店',
						phone: '021-9999999',
						remark: '门店描述:化妆品专卖店',
						address: '上海市徐家汇百盛大厦一楼'
					},
					{
						photo: '/static/swiper/2.jpg',
						name: '门店三:化妆品专卖店',
						phone: '021-9999999',
						remark: '门店描述:化妆品专卖店',
						address: '上海市徐家汇百盛大厦一楼'
					},

				],
				swiperList: [{
					id: 0,
					type: 'image',
					url: '/static/swiper/0.jpg'
				}, {
					id: 1,
					type: 'image',
					url: '/static/swiper/1.jpg'
				}, {
					id: 2,
					type: 'image',
					url: '/static/swiper/2.jpg'
				}, {
					id: 3,
					type: 'image',
					url: '/static/swiper/3.jpg'
				}, {
					id: 4,
					type: 'image',
					url: '/static/swiper/4.jpg'
				}, {
					id: 5,
					type: 'image',
					url: '/static/swiper/5.jpg'
				}, {
					id: 6,
					type: 'image',
					url: '/static/swiper/6.jpg'
				}]
			};
		},
		methods: {
			goDetail: function(e) {
				uni.navigateTo({
					url: "./listDetail?detailDate=" + encodeURIComponent(JSON.stringify(e))
				})
			},
			getStoreLit:function(){
				uni.request({
					url: common.webUrl + 'store/getAllList',
					data: this.loginForm,
					method: 'GET',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
						   this.listData = res.data.resultList
						}
					}
				});
			}
		},
		mounted() {
			this.getStoreLit()
		},
		onShow() {
			this.getStoreLit()
		}
	}
</script>

<style lang="scss">
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

	.uni-list {
		background-color: #FFFFFF;
		position: relative;
		width: 100%;
		display: flex;
		flex-direction: column;
	}

	.uni-list:after {
		position: absolute;
		z-index: 10;
		right: 0;
		bottom: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.uni-list::before {
		position: absolute;
		z-index: 10;
		right: 0;
		top: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.uni-list-cell {
		position: relative;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
	}

	.uni-list-cell-hover {
		background-color: #eee;
	}

	.uni-list-cell::after {
		position: absolute;
		z-index: 3;
		right: 0;
		bottom: 0;
		left: 30upx;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.uni-list .uni-list-cell:last-child::after {
		height: 0upx;
	}

	/* 图文列表 */
	.uni-media-list {
		padding: 22upx 30upx;
		box-sizing: border-box;
		display: flex;
		width: 100%;
		flex-direction: row;
	}

	.uni-navigate-right.uni-media-list {
		padding-right: 74upx;
	}

	.uni-pull-right {
		flex-direction: row-reverse;
	}

	.uni-pull-right>.uni-media-list-logo {
		margin-right: 0upx;
		margin-left: 20upx;
	}

	.uni-media-list-logo image {
		height: 100%;
		width: 100%;
	}


	.uni-media-list-text-bottom {
		width: 100%;
		line-height: 30upx;
		font-size: 26upx;
		color: #8f8f94;
	}

	.uni-media-list-logo {
		width: 180upx;
		height: 140upx;
		margin-right: 20upx;
	}

	.uni-media-list-body {
		display: flex;
		flex: 1;
		flex-direction: column;
		justify-content: space-between;
		align-items: flex-start;
		overflow: hidden;
		height: auto;
	}

	.uni-media-list-text-top {
		width: 100%;
		line-height: 36upx;
		font-size: 30upx;
		height: 74upx;
		font-size: 28upx;
		overflow: hidden;
	}

	.uni-media-list-text-bottom {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
