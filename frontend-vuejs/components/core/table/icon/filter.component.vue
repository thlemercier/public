<template>
    <div class="search-ico">
        <span @click="$emit('click')">
            <svg class="icon icon-filter_list"><use xlink:href="#icon-filter_list"></use></svg>
        </span>
        <symbol id="icon-filter_list" viewBox="0 0 16 16">
            <path d="M4 8.656v-1.313h8v1.313h-8zM2 4h12v1.344h-12v-1.344zM6.656 12v-1.344h2.688v1.344h-2.688z"></path>
        </symbol>
    </div>
</template>

<script>
export default {

};
</script>
<style>
.icon-filter_list {
  width: 12px;
  height: 12px;
  fill: rgba(255, 255, 255, 0.6);
}
</style>
