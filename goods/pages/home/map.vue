<template>
    <view>
        <view class="page-body">
            <view class="page-section page-section-gap">
                <map style="width: 100%; height: 300px;" :latitude="latitude" :longitude="longitude" :markers="covers">
                </map>
            </view>
        </view>
    </view>
</template>

<script>
	export default {
	   data() {
	        return {
	            title: 'map',
	            latitude: 39.909,
	            longitude: 116.39742,
	            covers: [{
	                latitude: 39.909,
	                longitude: 116.39742,
	                iconPath: '/static/image/map.png',
					width: 30, 
					height: 30
	            }],
				receiveObj:{}
	        }
	    },
		onLoad(event) {
			// 目前在某些平台参数会被主动 decode，暂时这样处理。
			try {
				this.receiveObj = JSON.parse(decodeURIComponent(event.detailDate));
			} catch (error) {
				this.receiveObj = JSON.parse(event.detailDate);
			}
			
		},
		mounted() {
			this.latitude = this.receiveObj.latitude
			this.longitude = this.receiveObj.longitude
			this.covers = [{
	                latitude: this.receiveObj.latitude,
	                longitude: this.receiveObj.longitude,
	                iconPath: '/static/image/map.png',
					width: 30, 
					height: 30
	            }]
			console.log(this.latitude)
			console.log(this.longitude)
		}
	}
</script>

<style lang="scss">

</style>
