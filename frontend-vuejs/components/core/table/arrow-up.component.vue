<template>
    <div class="arrow-up-ico">
        <span @click="$emit('click')">
            <svg class="icon icon-arrow-up2"><use xlink:href="#icon-arrow-up2"></use></svg>
        </span>
        <symbol id="icon-arrow-up2" viewBox="0 0 16 16">
          <path d="M13.707 6.293l-5-5c-0.39-0.391-1.024-0.391-1.414 0l-5 5c-0.391 0.391-0.391 1.024 0 1.414s1.024 0.391 1.414 0l3.293-3.293v9.586c0 0.552 0.448 1 1 1s1-0.448 1-1v-9.586l3.293 3.293c0.195 0.195 0.451 0.293 0.707 0.293s0.512-0.098 0.707-0.293c0.391-0.391 0.391-1.024 0-1.414z"></path>
        </symbol>
    </div>
</template>

<script>
export default {

};
</script>
<style>
  .icon-arrow-up2 {
    width: 12px;
    height: 12px;
    fill: rgba(255, 255, 255, 0.6);
  }
</style>
