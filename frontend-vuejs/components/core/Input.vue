<template>
  <div>
    <q-input
      :value="value" :stack-label="label" :placeholder="placeholder"
      :clearable="clearable" :class="{'no-border': noBorder}" @input="$emit('input', $event)"/>
  </div>
</template>

<script>
export default {
  props: ['value', 'label', 'placeholder', 'clearable', 'noBorder']
};
</script>

<style>
  .no-border:before, .no-border:after {
    border: none;
  }
</style>
