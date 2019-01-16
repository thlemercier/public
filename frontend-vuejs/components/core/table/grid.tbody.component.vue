<template>
    <div class="vg-tbody-container">
        <slot></slot>
    </div>
</template>

<script>
export default {
};
</script>

<style>
    .vg-tbody-container{
        display: grid;
        grid-template-columns: 1fr;
        color: #FFFFFF;
    }
</style>
