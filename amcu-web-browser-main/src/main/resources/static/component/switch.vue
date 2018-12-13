<template>
    <div class="weui-div" @click.stop="toggle">
        <span class="weui-switch" :class="{'weui-switch-on' : switchChecked}" :value="value" ></span>
        <div v-if='!value' class="weui-div-close">关闭</div>
        <div v-if='value' class="weui-div-open">开启</div>
    </div>
</template>

<script>
    export default {
        name: "switch",
        props: {
            value: {
                type: Boolean,
                default: true
            },
            toId: {
                type: String,
                default: '',
            },
            index: {
                type: Number,
                default:11090,
            },
            handle: {
                type: Boolean,
                default: true
            },
        },
        data() {
            return {
                switchChecked: this.value
            }
        },
        watch: {
            value(){
                this.switchChecked = this.value;
            }
        },
        methods: {
            toggle() {
                if(this.handle === true){
                    this.switchChecked = !this.switchChecked;
                    this.$emit('changeSwitch', this.switchChecked,this.toId,this.index);
                }

            }
        }
    }
</script>

<style scoped>

    .weui-div{
        position:relative;
        font-weight: bold;
        cursor:pointer;
        width: 82px;
        height: 32px;
    }

    .weui-div-open{
        position:absolute;
        left:10px;
        top:0;
        line-height:32px;
        font-size:12px;
        color: green;
    }

    .weui-div-close{
        position:absolute;
        right:11px;
        top:0;
        line-height:32px;
        font-size:12px;
        color: #999999;
    }

    .weui-switch {
        display: block;
        position: relative;
        width: 82px;
        height: 32px;
        border: 1px solid #DFDFDF;
        outline: 0;
        border-radius: 16px;
        box-sizing: border-box;
        background-color: #DFDFDF;
        transition: background-color 0.1s, border 0.1s;
        cursor: pointer;
    }

    .weui-switch:before {
        content: " ";
        position: absolute;
        top: 0;
        left: 0;
        width: 80px;
        height: 30px;
        border-radius: 15px;
        background-color: #E6E6E6;
        transition: transform 0.35s cubic-bezier(0.45, 1, 0.4, 1);
    }

    .weui-switch:after {
        content: " ";
        position: absolute;
        top: 0;
        left: 0;
        width: 30px;
        height: 30px;
        border-radius: 15px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);
        transition: transform 0.35s cubic-bezier(0.4, 0.4, 0.25, 1.35);
    }

    .weui-switch-on {
        border-color: #F2DC37;
        background-color: #F2DC37;
    }

    .weui-switch-on:before {
        border-color: #F2DC37;
        background-color: #F2DC37;
    }

    .weui-switch-on:after {
        transform: translateX(50px);
    }
</style>