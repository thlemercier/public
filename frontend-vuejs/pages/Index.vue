<template>
  <div>
    <section v-if="!showAddNewSource" class="row content-center justify-end q-pa-xs" style="height: 85px;">
      <fio-button
        v-if="!showAddNewSource"
        icon="add" label="New Source" bg-color="neutral"
        text-color="neutral"
        @click="showAddNewSource = true" />
    </section>
    <section v-else  class="shown row content-center justify-end q-pa-xs" style="height: 85px;">
      <transition name="fade">
        <div class="full-width">
          <form v-if="showAddNewSource" class="row justify-around no-wrap">
            <fio-input
              v-model="text" color="primary" placeholder="http://www.site.com" label="URL"
              clearable />
            <fio-input
              v-model="text" color="primary" placeholder="site.com" label="Domain"
              clearable />
            <fio-input
              v-model="text" color="primary" placeholder="^.*\/[0-9]{8}" label="Pattern"
              clearable />
            <fio-input
              v-model="text" color="primary" placeholder="^.*\/[0-9]{8}" label="Excluded Pattern"
              clearable />
            <fio-button
              v-if="showAddNewSource"
              icon="done" bg-color="neutral" text-color="neutral"
              class="q-ml-md"
              @click="showAddNewSource = false" />
            <fio-button
              v-if="showAddNewSource"
              icon="close" bg-color="danger" text-color="danger"
              class="q-ml-md"
              @click="showAddNewSource = false" />
          </form>
        </div>
      </transition>
    </section>
    <section v-if="sources">
      <vg-table :vgData.sync="sources">
        <vg-thead :sticky="true">
          <vg-row height='48px'>
            <vg-cell width="75px" filterKey="id" sort="asc" :groupStart="true" :sticky="true">ID</vg-cell>
            <vg-cell sort="asc" filterKey="domain" :groupStart="true">Domain</vg-cell>
            <vg-cell sort="asc" filterKey="url">URL</vg-cell>
            <vg-cell>Pattern</vg-cell>
            <vg-cell sort="asc">Excluded Pattern</vg-cell>
            <vg-cell>Last Crawl</vg-cell>
          </vg-row>
        </vg-thead>
        <vg-tbody>
          <vg-row  height='78px' v-for="source in sources" :key="source.id">
            <vg-cell width="75px" :groupStart="true" :sticky="true">{{source.id}}</vg-cell>
            <vg-cell :groupStart="true" >{{source.domain}}</vg-cell>
            <vg-cell>{{source.url}}</vg-cell>
            <vg-cell>{{source.pattern}}</vg-cell>
            <vg-cell>{{source.excludedPattern}}</vg-cell>
            <vg-cell>{{getLastCrawl(source)}}</vg-cell>
          </vg-row>
        </vg-tbody>
    </vg-table>
    </section>
  </div>
</template>

<script>
import FioButton from 'src/components/core/Button';
import FioInput from 'src/components/core/Input';
import VgTable from 'src/components/core/table/grid.table.component';
import VgThead from 'src/components/core/table/grid.thead.component';
import VgTbody from 'src/components/core/table/grid.tbody.component';
import VgRow from 'src/components/core/table/grid.row.component';
import VgCell from 'src/components/core/table/grid.cell.component';
export default {
  name: 'PageIndex',
  components: {
    FioButton,
    FioInput,
    VgTable,
    VgThead,
    VgTbody,
    VgRow,
    VgCell
  },
  data () {
    return {
      showAddNewSource: false,
      sources: null
    };
  },
  mounted () {
    this.load();
  },
  methods: {
    async load () {
      const response = await this.$axios.get('http://localhost:8081/api/sources');

      this.sources = response.data;
      console.log(response.data);
      console.log(this.sources);
    },
    getLastCrawl (source) {
      console.log(source.crawlerInfos);
      return 'a';
    }
  }
};
</script>

<style lang="stylus" scoped>
  @import '~variables';
  .shown {
    background: $secondary;
    transition: background .5s;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-leave-active {
    transition: opacity 0s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
</style>
