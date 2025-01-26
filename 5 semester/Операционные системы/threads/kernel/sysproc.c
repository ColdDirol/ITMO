#include "types.h"
#include "riscv.h"
#include "defs.h"
#include "param.h"
#include "memlayout.h"
#include "spinlock.h"
#include "proc.h"

uint64
sys_exit(void)
{
  int n;
  argint(0, &n);
  exit(n);
  return 0;  // not reached
}

uint64
sys_getpid(void)
{
  return myproc()->pid;
}

uint64
sys_fork(void)
{
  return fork();
}

uint64
sys_wait(void)
{
  uint64 p;
  argaddr(0, &p);
  return wait(p);
}

uint64
sys_sbrk(void)
{
  uint64 addr;
  int n;

  argint(0, &n);
  addr = myproc()->sz;
  if(growproc(n) < 0)
    return -1;
  return addr;
}

uint64
sys_sleep(void)
{
  int n;
  uint ticks0;

  argint(0, &n);
  if(n < 0)
    n = 0;
  acquire(&tickslock);
  ticks0 = ticks;
  while(ticks - ticks0 < n){
    if(killed(myproc())){
      release(&tickslock);
      return -1;
    }
    sleep(&ticks, &tickslock);
  }
  release(&tickslock);
  return 0;
}

uint64
sys_kill(void)
{
  int pid;

  argint(0, &pid);
  return kill(pid);
}

// return how many clock tick interrupts have occurred
// since start.
uint64
sys_uptime(void)
{
  uint xticks;

  acquire(&tickslock);
  xticks = ticks;
  release(&tickslock);
  return xticks;
}


// THREADS
uint64
sys_clone(void)
{
  uint64 function; // адрес функции
  uint64 arg;      // аргумент для функции
  uint64 stack;    // адрес стека

  // Извлекаем аргументы
  argaddr(0, &function);
  argaddr(1, &arg);
  argaddr(2, &stack);

  // Вызываем функцию clone и возвращаем результат
  return clone((void (*)(void *))function, (void *)arg, (void *)stack);
}


uint64
sys_join(void)
{
  int tid;        // идентификатор потока
  uint64 stack;   // указатель для возврата адреса стека

  // Извлекаем аргументы
  argint(0, &tid);
  argaddr(1, &stack);

  // Вызываем функцию join
  void *stack_ptr;
  int result = join(tid, &stack_ptr);

  // Если указатель stack не равен 0, копируем адрес стека в пользовательское пространство
  if (stack != 0) {
    if (copyout(myproc()->pagetable, stack, (char *)&stack_ptr, sizeof(stack_ptr)) < 0) {
      return -1; // Ошибка копирования
    }
  }

  return result;
}
