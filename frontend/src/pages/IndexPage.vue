<template>
  <div class="q-px-xl q-pb-md">
    <div class="row items-center">
      <div class="col-12 q-my-lg">
        <q-table
          :columns="columns"
          :rows="toDos"
          row-key="name"
        >
          <template v-slot:top>
            <q-space/>
            <q-btn
              @click="isAddToDoDialogOpen=true"
              color="accent"
              icon="add"
              flat
            />
            <div style="width: 100%" class="row q-col-gutter-sm">
              <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12">
                <div> Done?</div>
                <q-checkbox left-label v-model="filter.isDone" label="yes"/>
                <q-checkbox left-label v-model="filter.isNotDone" label="no"/>
              </div>
              <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12">
                <q-input
                  filled
                  v-model="filter.description"
                  label="Description"
                >
                  <template v-slot:append>
                    <q-icon name="search"/>
                  </template>
                </q-input>
              </div>
              <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12">
                <q-input
                  filled
                  v-model="filterDate"
                  label="Due Date"
                  mask="##.##.####"
                  placeholder="DD.MM.YYYY"
                  stack-label
                >
                  <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer" @click="openDatePicker = true"/>
                  </template>
                </q-input>

                <q-dialog v-model="openDatePicker">
                  <q-date
                    v-model="filterDate"
                    mask="DD.MM.YYYY"
                  />
                </q-dialog>
              </div>
              <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12">
                <q-btn
                  @click="onRequest"
                  color="accent"
                  label="apply filter"
                  icon="filter_list"
                  flat
                />
                <q-btn
                  v-if="filter.description || filterDate || filter.isDone || filter.isNotDone"
                  @click="removeFilter"
                  color="negative"
                  label="remove filter"
                  icon="cancel"
                  flat
                />
              </div>
            </div>
          </template>
          <template v-slot:body-cell-dueDate="props">
            <q-td :props="props">
              {{ formatDueDate(props.row.dueDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-createdAt="props">
            <q-td :props="props">
              {{ formatDueDate(props.row.createdAt) }}
            </q-td>
          </template>
          <template v-slot:body-cell-isDone="props">
            <q-td :props="props">
              <q-checkbox
                v-model="props.row.done"
                @update:model-value="toggleIsDone(props.row)"
                color="primary"
              />
            </q-td>
          </template>
          <template v-slot:body-cell-action="props">
            <q-td :props="props">
              <q-btn
                flat
                dense
                @click="openEditToDo(props.row)"
                icon="edit"
                color="accent"
              />
              <q-btn
                flat
                dense
                @click="openDeleteToDoConfirmation(props.row.id)"
                icon="delete"
                color="negative"
              >
              </q-btn>
            </q-td>
          </template>
        </q-table>
      </div>
    </div>

    <q-dialog v-model="isAddToDoDialogOpen" full-width>
      <q-card>
        <q-card-section>
          <div class="text-h6"> Add To Do</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-form @submit="addToDo" class="q-gutter-md">
            <q-input
              filled
              v-model="description"
              label="Description *"
              :rules="[ val => val && val.length > 0 || 'Description is required']"
            />

            <q-input
              filled
              v-model="dateString"
              label="Due Date"
              mask="##.##.####"
              placeholder="DD.MM.YYYY"
              stack-label
            >
              <template v-slot:append>
                <q-icon name="event" class="cursor-pointer" @click="openDatePicker = true"/>
              </template>
            </q-input>

            <q-dialog v-model="openDatePicker">
              <q-date
                v-model="dateString"
                mask="DD.MM.YYYY"
              />
            </q-dialog>

            <q-card-actions align="right">
              <q-btn
                @click="resetAddToDoFields"
                label="Cancel"
                color="negative"
                v-close-popup
                flat
              />
              <q-btn
                type="submit"
                label="Add To Do"
                color="accent"
                flat
              />
            </q-card-actions>
          </q-form>
        </q-card-section>
      </q-card>
    </q-dialog>

    <q-dialog v-model="isEditToDoDialogOpen" full-width>
      <q-card>
        <q-card-section>
          <div class="text-h6"> Edit To Do</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-form @submit="editToDo" class="q-gutter-md">
            <q-input
              filled
              v-model="editableDescription"
              label="Description *"
              :rules="[ val => val && val.length > 0 || 'Description is required']"
            />

            <q-input
              filled
              v-model="editableDueDate"
              label="Due Date"
              mask="##.##.####"
              placeholder="DD.MM.YYYY"
              stack-label
            >
              <template v-slot:append>
                <q-icon name="event" class="cursor-pointer" @click="openDatePicker = true"/>
              </template>
            </q-input>

            <q-dialog v-model="openDatePicker">
              <q-date
                v-model="editableDueDate"
                mask="DD.MM.YYYY"
              />
            </q-dialog>

            <q-card-actions align="right">
              <q-btn
                @click="resetAddToDoFields"
                label="Cancel"
                color="negative"
                v-close-popup
                flat
              />
              <q-btn
                type="submit"
                label="Edit To Do"
                color="accent"
                flat
              />
            </q-card-actions>
          </q-form>
        </q-card-section>
      </q-card>
    </q-dialog>

    <q-dialog v-model="isDeleteToDoConfirmationOpen">
      <q-card>
        <q-card-section>
          <div class="text-h6"> Delete To Do</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-card-actions align="right">
            <q-btn
              label="Cancel"
              color="accent"
              flat
              v-close-popup
            />
            <q-btn
              @click="deleteToDo"
              label="Delete To Do"
              color="negative"
              flat
              v-close-popup
            />
          </q-card-actions>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {api} from 'boot/axios'

const toDos = ref([]);
const filter = ref({
  description: '',
  dueDate: null,
  isDone: false,
  isNotDone: false
});
const filterDate = ref(null);
const isAddToDoDialogOpen = ref(false);
const openDatePicker = ref(false);
const description = ref(null);
const isDone = ref(false);
const dueDate = ref(null);
const dateString = ref(null);
const isEditToDoDialogOpen = ref(false);
const editableToDoId = ref(null);
const editableDescription = ref(null);
const editableIsDone = ref(false);
const editableDueDate = ref(null);
const isDeleteToDoConfirmationOpen = ref(false);
const deletableToDoId = ref(null);
const columns = [
  {
    name: 'description',
    label: 'Description',
    field: 'description',
    required: false,
    align: 'left',
    sortable: false
  },
  {
    name: 'dueDate',
    label: 'Due Date',
    field: 'dueDate',
    required: false,
    align: 'left',
    sortable: false
  },
  {
    name: 'createdAt',
    label: 'Creation Date',
    field: 'createdAt',
    required: false,
    align: 'left',
    sortable: false
  },
  {
    name: 'isDone',
    label: 'Done',
    field: 'isDone',
    required: false,
    align: 'left',
    sortable: false
  },
  {
    name: 'action',
    align: 'right',
    label: '',
    field: ''
  }
];

onMounted(() => {
  getToDos();
})

function getToDos() {
  api.get('/todo', {
    params: {
      description: filter.value.description,
      dueDate: filter.value.dueDate,
      isDone: filter.value.isDone,
      isNotDone: filter.value.isNotDone
    }
  })
    .then(response => {
      toDos.value = response.data;
    })
}

function onRequest() {
  if (filterDate.value === null) {
    filter.value.dueDate = null;
  } else {
    filter.value.dueDate = convertToISO(filterDate.value);
  }
  getToDos();
}

function removeFilter() {
  filter.value.description = '';
  filter.value.dueDate = null;
  filter.value.isDone = false;
  filter.value.isNotDone = false;
  filterDate.value = null;
  onRequest();
}

function formatDueDate(dueDate) {
  if (dueDate === null) {
    return '';
  }
  const date = new Date(dueDate);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();

  return `${day}.${month}.${year}`;
}

function convertToISO(dateString) {
  const [day, month, year] = dateString.split(".");

  return `${year}-${month}-${day}`;
}

function addToDo() {
  if (dateString.value === null) {
    dueDate.value = null;
  } else {
    dueDate.value = convertToISO(dateString.value);
  }
  api.post('/todo', {
    description: description.value,
    isDone: isDone.value,
    dueDate: dueDate.value,
  })
    .then(() => {
      getToDos();
      isAddToDoDialogOpen.value = false;
      resetAddToDoFields();
    })
}

function resetAddToDoFields() {
  description.value = null;
  isDone.value = false;
  dueDate.value = null;
  dateString.value = null;
  openDatePicker.value = false;
  isAddToDoDialogOpen.value = false;
  isEditToDoDialogOpen.value = false;
  editableDescription.value = null;
  editableIsDone.value = false;
  editableDueDate.value = null;
}

function openEditToDo(row) {
  isEditToDoDialogOpen.value = true;
  editableToDoId.value = row.id;
  editableDescription.value = row.description;
  editableIsDone.value = row.isDone;
  editableDueDate.value = formatDueDate(row.dueDate);
}

function editToDo() {
  if (editableDueDate.value === null) {
    dueDate.value = null;
  } else {
    dueDate.value = convertToISO(editableDueDate.value);
  }
  api.patch(`/todo/${editableToDoId.value}`, {
    description: editableDescription.value,
    isDone: editableIsDone.value,
    dueDate: dueDate.value,
  })
    .then(() => {
      getToDos();
      isEditToDoDialogOpen.value = false;
    })
}

function toggleIsDone(row) {
  api.patch(`/todo/${row.id}`, {
    description: row.description,
    isDone: row.done, // Updated status
    dueDate: row.dueDate,
  })
    .then(() => {
      getToDos(); // Refresh the list after successful update
    })
    .catch(() => {
      row.isDone = !row.isDone;
    });
}

function openDeleteToDoConfirmation(id) {
  isDeleteToDoConfirmationOpen.value = true;
  deletableToDoId.value = id;
}

function deleteToDo() {
  api.delete(`/todo/${deletableToDoId.value}`)
    .then(() => {
      getToDos();
      isDeleteToDoConfirmationOpen.value = false;
    })
}

</script>
