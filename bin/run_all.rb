
n = 16
infname = "input80000.txt"
loops = 3
repeats = 3

n.times do |j|
  system("echo 'Group#{j}\n'")
  system("echo '\n Group#{j}: \n' >> results.txt")
  repeats.times do 
    system("java Group#{j} #{infname} out#{j}.txt #{loops} >> results.txt") 
  end
  system("echo 'Results of diff:\n' >> results.txt")
  system("diff --ignore-all-space --brief out#{j}.txt out0.txt >> results.txt")
# keep the output file this time:     
  #  system("rm out#{j}.txt")       
end


